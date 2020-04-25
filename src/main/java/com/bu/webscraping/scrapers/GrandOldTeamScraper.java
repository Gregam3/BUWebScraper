package com.bu.webscraping.scrapers;

import com.bu.forum.ForumType;
import com.bu.webscraping.Login;

/**
 * @author Greg Mitten (i7676925)
 * gregoryamitten@gmail.com
 */
public class GrandOldTeamScraper extends AbstractScraper {
    //email: onerous3@gmail.com
    //password: password

    public GrandOldTeamScraper() {
        setPostPattern("data-lb-caption-desc=\"([\\S\\s]*?)·([\\S\\s]*?)\">" +
                "[\\S\\s]*?<div class=\"bbWrapper\">([\\S\\s]*?)<div class=\"js-selectToQuoteEnd\">");
        setPageUrlPrefix("/page-");
        setPostGroupIndexes(new int[]{3, 1, 2});

        setLastPagePatternLong("js-pageJumpPage\" value=\"[0-9]+\" min=\"[0-9]+\" max=\"([0-9]+)\" step=\"[0-9]+\"");

        setForumSizePattern("Messages[\\S\\s]*?</dt>[\\S\\s]*?<dd>[\\S\\s]*?([0-9,]+)");

        setQuotePattern("<div class=\"bbCodeBlock-expandContent\">([\\S\\s]*?)<div class=\"bbCodeBlock-expandLink\">" +
                "[\\S\\s]*?</a>([\\S\\s]*)");

        //Setting up login details and the necessary cookies
        Login login = new Login("onerous3@gmail.com", "password", "https://www.grandoldteam.com/forum/login/login");

        login.addCookie("xf_session", "");
        setLogin(login);

        setForumType(ForumType.EVERTON_GRAND_OLD_TEAM);
    }
}
