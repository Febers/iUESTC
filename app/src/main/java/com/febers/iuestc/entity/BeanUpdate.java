/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-8-9 下午2:09
 *
 */

package com.febers.iuestc.entity;

/**
 * {
 "url": "https://api.github.com/repos/Febers/iUESTC/releases/12323367",
 "assets_url": "https://api.github.com/repos/Febers/iUESTC/releases/12323367/assets",
 "upload_url": "https://uploads.github.com/repos/Febers/iUESTC/releases/12323367/assets{?name,label}",
 "html_url": "https://github.com/Febers/iUESTC/releases/tag/12",
 "id": 12323367,
 "node_id": "MDc6UmVsZWFzZTEyMzIzMzY3",
 "tag_name": "12",
 "target_commitish": "master",
 "name": "Beta2.9",
 "draft": false,
 "author": {
 "login": "Febers",
 "id": 26399388,
    ...
 },
 "prerelease": false,
 "created_at": "2018-08-09T05:58:18Z",
 "published_at": "2018-08-09T06:54:48Z",
 "assets": [
 {
 "url": "https://api.github.com/repos/Febers/iUESTC/releases/assets/8182170",
    ...
 },
 "content_type": "application/vnd.android.package-archive",
 "state": "uploaded",
 "size": 3208489,
 "download_count": 1,
 "created_at": "2018-08-09T06:54:25Z",
 "updated_at": "2018-08-09T06:54:35Z",
 "browser_download_url": "https://github.com/Febers/iUESTC/releases/download/12/github_test.apk"
 }
 ],
 "tarball_url": "https://api.github.com/repos/Febers/iUESTC/tarball/12",
 "zipball_url": "https://api.github.com/repos/Febers/iUESTC/zipball/12",
 "body": "版本说明"
 }
 */
public class BeanUpdate {
    private String tagName;
    private String versionName;
    private String downloadUrl;
    private String body;
    private String size;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
