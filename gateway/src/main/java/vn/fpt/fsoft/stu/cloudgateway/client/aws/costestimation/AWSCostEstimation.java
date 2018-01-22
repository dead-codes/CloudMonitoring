package vn.fpt.fsoft.stu.cloudgateway.client.aws.costestimation;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import vn.fpt.fsoft.stu.cloudgateway.caching.FCache;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component
public class AWSCostEstimation {

    private static Logger LOGGER = LoggerFactory.getLogger(AWSCostEstimation.class);

    public void initializeCostData() throws Exception {
        Class<?> costEstimationClass = this.getClass();
        Object awsServiceNameObject = costEstimationClass.getMethod("getServiceName").invoke(this);
        if (awsServiceNameObject == null) {
            return;
        }

        String awsServiceName = awsServiceNameObject.toString();

        for (Field field : costEstimationClass.getFields()) {
            int mods = field.getModifiers();
            if (Modifier.isStatic(mods) && Modifier.isPublic(mods) && field.getType() == String.class
                    && field.getName().startsWith(awsServiceName) && field.getName().endsWith("URL")) {
                String fieldValue = field.get(null).toString();
                try {
                    FCache fCache = FCache.getCache(fieldValue);
                    fCache.set(fieldValue, downloadCostData(fieldValue), 1 * FCache.MINUTE);
                } catch (Exception ex) {
                    LOGGER.error("Unable to download: " + fieldValue, ex);
                }
            }
        }
    }

    protected double getCost(String link, String pattern) throws IOException {
        String costData = getCostData(link);
        JSONArray jsonPrices = JsonPath.read(costData, pattern);

        if (jsonPrices == null || jsonPrices.isEmpty()) {
            LOGGER.error("Cost not found for pattern " + pattern + " from URL " + link);
            return 0;
        }

        return Double.parseDouble(jsonPrices.get(0).toString());
    }

    protected String getCostData(String link) throws IOException {
        FCache fCache = FCache.getCache(link);
        Object data = fCache.get(link);

        if (data == null) {
            data = downloadCostData(link);
            fCache.set(link, data, 1 * FCache.DAY);
        }

        return data.toString();
    }

    public String downloadCostData(String link) throws IOException {
        LOGGER.debug("Start downloading: " + link + " ... ");

        URL url = new URL(link);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(HttpMethod.GET.toString());
        connection.setDoInput(true);

        StringBuilder content = new StringBuilder();

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
        } catch (IOException ex) {
            throw ex;
        }

        LOGGER.debug("Successfully downloaded: " + link);
        return content.substring(content.indexOf("callback(") + 9, content.lastIndexOf(")"));
    }
}
