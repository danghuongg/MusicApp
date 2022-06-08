package com.danghuong.musicapp.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SearchedHistoryItem {
    @PrimaryKey(autoGenerate = true)
    private Long iTopic;
    private String name;

    public SearchedHistoryItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getITopic() {
        return iTopic;
    }
    public void setITopic(Long iTopic ) {
        this.iTopic = iTopic;
    }
}
