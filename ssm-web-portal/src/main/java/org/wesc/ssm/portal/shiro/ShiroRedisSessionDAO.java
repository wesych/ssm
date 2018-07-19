package org.wesc.ssm.portal.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wesc.ssm.dao.cache.JdkRedisTemplateUtil;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 使用Redis保存会话信息来完成持久化操作(JDK进行序列化而非Json工具序列化).
 *
 * @author Wesley Cheung
 * @Date Created in 15:44 2017/12/27
 */
public class ShiroRedisSessionDAO extends AbstractSessionDAO {

    /** LOG */
    private static Logger logger = LoggerFactory.getLogger(ShiroRedisSessionDAO.class);

    /** 过期时间(分钟) */
    private static final int SESSION_EXPIRE_TIME = 60 * 24;

    @Override
    public void update(Session session) throws UnknownSessionException {
        saveSession(session);
    }

    /**
     * save session
     * @param session
     * @throws UnknownSessionException
     */
    private void saveSession(Session session) throws UnknownSessionException{
        if(session == null || session.getId() == null){
            logger.error("session or session id is null");
            return;
        }
        String key = getStringKey(session.getId());
        JdkRedisTemplateUtil.valueSet(key, session, SESSION_EXPIRE_TIME, TimeUnit.MINUTES);
    }

    @Override
    public void delete(Session session) {
        if(null == session || null == session.getId()){
            logger.error("session or session id is null");
            return;
        }
        JdkRedisTemplateUtil.delete(getStringKey(session.getId()));
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<>();
        Set<String> keys = JdkRedisTemplateUtil.keys(ShiroRedisKey.SHIRO_SESSION_KEY_PREFIX + "*");
        if(null != keys && keys.size()>0){
            for(String key:keys){
                Session s = (Session) JdkRedisTemplateUtil.valueGet(key);
                sessions.add(s);
            }
        }
        return sessions;
    }

    @Override
    protected Serializable doCreate(Session session) {
        /** create sessionId */
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);

        /** save session */
        saveSession(session);

        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if(null == sessionId){
            logger.error("shiro session id is null");
            return null;
        }
        Session session = (Session) JdkRedisTemplateUtil.valueGet(getStringKey(sessionId));
        return session;
    }

    /**
     * 获得String型的key
     * @param sessionId
     * @return
     */
    private String getStringKey(Serializable sessionId){
        return ShiroRedisKey.createShiroSessionKey(ShiroRedisKey.SHIRO_SESSION_KEY_PREFIX + sessionId);
    }

}
