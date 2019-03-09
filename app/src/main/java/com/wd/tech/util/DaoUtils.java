package com.wd.tech.util;


import com.wd.tech.application.WDApplication;
import com.wd.tech.dao.DaoMaster;
import com.wd.tech.dao.DaoSession;
import com.wd.tech.dao.FindConversationListDao;

public class DaoUtils {
    private FindConversationListDao conversationDao;

    private static DaoUtils instance;

    private DaoUtils(){
        DaoSession session = DaoMaster.newDevSession(WDApplication.getAppContext(), FindConversationListDao.TABLENAME);
        conversationDao = session.getFindConversationListDao();
    }

    public static DaoUtils getInstance() {
        if (instance==null){
            return new DaoUtils();
        }
        return instance;
    }

    public FindConversationListDao getConversationDao() {
        return conversationDao;
    }
}
