package com.fieldwire.imgurimagesearchapp.data.model;

import java.util.List;

/*

{
"id": "qCLoeTg",
            "title": "(IG: orrinsworld)  40k Chimera Armoured troop transport, because dieing in a sweaty fireball is more preferable than being taken by the Dark Eldar",
            "description": null,
            "datetime": 1597706589,
            "cover": "zddYiQU",
            "cover_width": 3000,
            "cover_height": 2250,
            "account_url": "Orrinsworld",
            "account_id": 87282491,
            "privacy": "hidden",
            "layout": "blog",
            "views": 35,
            "link": "https://imgur.com/a/qCLoeTg",
            "ups": 1,
            "downs": 1,
            "points": 0,
            "score": 0,
            "is_album": true,
            "vote": null,
            "favorite": false,
            "nsfw": false,
            "section": "",
            "comment_count": 0,
            "favorite_count": 0,
            "topic": "No Topic",
            "topic_id": 29,
            "images_count": 5,
            "in_gallery": true,
            "is_ad": false,
            "tags":
            "ad_type": 0,
            "ad_url": "",
            "in_most_viral": false,
            "include_album_ads": false,
            "images": [],
            "ad_config": {}
}
 */
public class FWGalleryItem {

    List<FWImageItem> fwImageItemList;

    public List<FWImageItem> getFwImageItemList() {
        return fwImageItemList;
    }

    public void setFwImageItemList(List<FWImageItem> fwImageItemList) {
        this.fwImageItemList = fwImageItemList;
    }

}
