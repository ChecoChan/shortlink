-- 设置用户访问频率限制的参数
local username = KEYS[1]
local timeWindow = tonumber(ARGV[1]) -- 时间窗口，单位：秒

-- 构造 Redis 中存储用户访问次数的键名
local accessKey = "short-link:user-flow-risk-control:" .. username

-- 使用SETNX命令设置键的值
local setnxResult = redis.call("SETNX", accessKey, "1")

-- 原子递增访问次数，并获取递增后的值
local currentAccessCount = redis.call("INCR", accessKey)

-- 只有设置键成功才给键设置过期时间
if setnxResult == 1 then
	redis.call("EXPIRE", accessKey, timeWindow)
end

-- 返回当前访问次数
return currentAccessCount
