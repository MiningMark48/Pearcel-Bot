package com.miningmark48.tidalbot.util.features;

import com.miningmark48.tidalbot.util.DataUtil;
import org.apache.commons.lang3.text.WordUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Based on code by Jared & Darkhax (thanks \uD83D\uDE1B)
public class CurseData {

    public static final String PROFILE_URL = "https://minecraft.curseforge.com/members/";

    public static final String PROJECTS_PAGE_URL = "https://minecraft.curseforge.com/members/%s/projects?page=";

    public static final String PROJECT_URL = "https://minecraft.curseforge.com/projects/";

    private String username;

    private String avatar;

    private String profile;

    private List<String> projectURLs;

    private Map<String, Long> downloads;

    private long totalDownloads;

    private boolean foundUser = true;

    private boolean foundProject = false;

    public CurseData(String username) {

        this.username = username;
        this.profile = PROFILE_URL + username;
        this.projectURLs = new ArrayList<>();
        this.downloads = new HashMap<>();

        final String baseURL = String.format(PROJECTS_PAGE_URL, username);

        int page = 1;
        boolean foundDuplicatePage = false;
        boolean foundAvatar = false;

        while (!foundDuplicatePage) {

            try (InputStream stream = getConnection(new URL(baseURL + page).openConnection()).getInputStream()) {

                final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                for (String line = reader.readLine(); line != null; line = reader.readLine())
                    // Find avatar
                    if (!foundAvatar && line.contains("<div class=\"avatar avatar-100 user user-role_block-curse-premium\">")) {

                        reader.readLine();
                        final String imageLine = reader.readLine();

                        if (imageLine.contains("<img src=")) {

                            this.avatar = imageLine.split("\"")[1];
                            foundAvatar = true;
                        }
                    }

                    // Find projects
                    else if (line.contains("a href=\"/projects/")) {

                        final String projectURL = "https://minecraft.curseforge.com" + line.split("\"")[1].split("\"")[0];

                        if (!this.projectURLs.contains(projectURL)) {

                            this.projectURLs.add(projectURL);
                            this.foundProject = true;
                        } else {

                            foundDuplicatePage = true;
                            break;
                        }
                    }

                if (page == 1 && !this.foundProject)
                    return;
            } catch (final Exception e) {

                if (e instanceof java.io.IOException && e.getMessage().contains("HTTP response code: 400") || e instanceof MalformedURLException || e instanceof FileNotFoundException && (e.getMessage().contains("https://minecraft.curseforge.com/not-found?404") || e.getMessage().contains("https://minecraft.curseforge.com/members/"))) {

                    this.foundUser = false;
                    break;
                } else
                    e.printStackTrace();
            }

            page++;
        }

        if (!this.foundUser || !this.foundProject)
            return;

        // downloads
        for (final String projectUrl : this.projectURLs)
            try (InputStream stream = getConnection(new URL(projectUrl).openConnection()).getInputStream()) {

                final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                boolean foundDownloads = false;

                for (String line = reader.readLine(); line != null; line = reader.readLine())
                    if (foundDownloads) {

                        final long projectDownloads = Long.parseLong(line.split(">")[1].split("<")[0].replaceAll(",", ""));
                        this.totalDownloads += projectDownloads;
                        final String name = projectUrl.replace("https://minecraft.curseforge.com/projects/", "").replaceAll("-", " ");

                        //this.downloads.put("[" + name + "](" + projectUrl.replaceAll(" ", "-") + ")", projectDownloads);
                        this.downloads.put(WordUtils.capitalize(name), projectDownloads);
                        break;
                    } else if (line.contains("Total Downloads"))
                        foundDownloads = true;
            } catch (final Exception e) {

                e.printStackTrace();
            }

        this.downloads = DataUtil.sortByValue(this.downloads, true);
    }

    public boolean exists() {

        return this.foundUser;
    }

    public String getUsername() {

        return this.username;
    }

    public String getAvatar() {

        return this.avatar;
    }

    public boolean hasAvatar() {

        return this.avatar != null && !this.avatar.isEmpty();
    }

    public String getProfile() {

        return this.profile;
    }

    public List<String> getProjectURLs() {

        return this.projectURLs;
    }

    public Map<String, Long> getDownloads() {

        return this.downloads;
    }

    public boolean hasProjects() {

        return this.foundProject && !this.projectURLs.isEmpty() && !this.downloads.isEmpty();
    }

    public int getProjectCount() {

        return this.projectURLs.size();
    }

    public long getTotalDownloads() {

        return this.totalDownloads;
    }

    private URLConnection getConnection(URLConnection connection) {
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        return connection;
    }
}
