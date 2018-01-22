package vn.fpt.fsoft.stu.cloudgateway.caching;

import org.apache.log4j.Logger;

import java.util.*;

public class FCache {

	public static final int MINUTE = 60;
	public static final int SECOND = 1;
	public static final int HOUR = 60 * MINUTE;
	public static final int DAY = 24 * HOUR;
	public static final int SCHEDULE_CHECK_CLEAN = 10 * MINUTE;
	public static final boolean use_cache = true;
	private static final Logger LOGGER = Logger.getLogger(FCache.class);
	public static Map<String, FCache> mapCache = Collections.synchronizedMap(new HashMap<String, FCache>());
	private static Date cleanCacheNextTime = new Date();
	public String category;
	public Map<String, CacheInfo> cache = Collections.synchronizedMap(new HashMap<String, CacheInfo>());
	public List<String> lstDependsOn = new ArrayList<String>();
	public boolean initDependsOn = false;

	public FCache(String category) {
		this.category = category;
		initDependsOn = false;
	}

	public static FCache getCache(String category) {
		FCache ret = null;
		try {
			ret = mapCache.get(category);

		} catch (Exception e) {

			LOGGER.error("getCache() Exception:" + e.getMessage());
		}
		if (ret == null) {
			ret = new FCache(category);
			mapCache.put(category, ret);

		}
		return ret;
	}

	public static FCache getCache(String category, String[] lstDependsOn) {
		FCache ret = null;
		try {
			ret = mapCache.get(category);
		} catch (Exception e) {
			LOGGER.error("getCache() Exception:" + e.getMessage());
		}
		if (ret == null) {
			ret = new FCache(category);
		}

		if (ret.initDependsOn == false) {
			ret.initDependsOn = true;
			for (String s : lstDependsOn) {
				FCache c = getCache(s);
				c.lstDependsOn.add(category);
			}
			mapCache.put(category, ret);
		}
		return ret;
	}

	public static void CleanCache(String category) {
		if (use_cache) {
			try {
				// mapCache.remove(category);
				FCache c = mapCache.get(category);
				if (c.cache.size() > 0) {
					for (String s : c.lstDependsOn) {
						CleanCache(s);
					}
				}
				c.cache.clear();
			} catch (Exception e) {
				LOGGER.error("CleanCache() OUT category:" + e.getMessage());
			}
		}
	}

	public void CheckAndCleanCache() {
		if (cleanCacheNextTime.compareTo(new Date()) < 0) {
			// LOGGER.debug("CheckAndCleanCache() IN");

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.SECOND, SCHEDULE_CHECK_CLEAN);
			cleanCacheNextTime = calendar.getTime();

			try {
				for (Map.Entry<String, FCache> entry : mapCache.entrySet()) {
					FCache c = entry.getValue();

					// LOGGER.debug("CheckAndCleanCache() check. category:" + entry.getKey());

					try {
						for (Map.Entry<String, CacheInfo> cacheEntry : c.cache.entrySet()) {
							CacheInfo ci = cacheEntry.getValue();

							if (ci.expireTime.compareTo(new Date()) < 0) {
								c.cache.remove(cacheEntry.getKey());
							}
						}
					} catch (Exception e) {
						LOGGER.error("CheckAndCleanCache() OUT category:" + e.getMessage());
					}
				}
			} catch (Exception e) {
				LOGGER.error("CheckAndCleanCache() OUT Exception: " + e.getMessage());
			}
		}
	}

	public Object get(String key) {
		if (use_cache) {
			CacheInfo ci = cache.get(key);

			if (ci == null) {
				return null;
			}
			if (ci.expireTime.compareTo(new Date()) < 0) {
				cache.remove(key);
				return null;
			}
			return ci.object;
		} else {
			return null;
		}

	}

	public Object get(String key, int exprireSeconds) {
		if (use_cache) {
			CacheInfo ci = cache.get(key);

			if (ci == null) {
				return null;
			}
			if (ci.expireTime.compareTo(new Date()) < 0) {
				cache.remove(key);
				return null;
			}

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.SECOND, exprireSeconds);
			ci.expireTime = calendar.getTime();

			return ci.object;
		} else {
			return null;
		}
	}

	public void set(String key, Object object, int exprireSeconds) {
		if (use_cache) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.SECOND, exprireSeconds);

			CacheInfo ci = new CacheInfo(object, calendar.getTime());
			cache.put(key, ci);
			CheckAndCleanCache();
		}
	}

	public void clean(String key) {
		try {
			cache.remove(key);
		} catch (Exception e) {
			LOGGER.error("clean() OUT category:" + e.getMessage());
		}
	}

	class CacheInfo {
		public Date expireTime;
		public Object object;

		public CacheInfo() {

		}

		public CacheInfo(Object object, Date expireTime) {
			this.object = object;
			this.expireTime = expireTime;
		}
	}
}
