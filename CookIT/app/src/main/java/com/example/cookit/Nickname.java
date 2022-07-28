package com.example.cookit;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Nickname {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nickname;

    public Nickname(String nickname) {
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "Nickname{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
