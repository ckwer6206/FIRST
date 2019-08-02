package com.kakao.kakaotalk.response.model;

/**
 * 플친관의 관계
 *
 * @since 1.17.0
 *
 * @author kevin.kang. Created on 2019-03-19..
 */
public enum PlusFriendRelation {
    /**
     * 추가된 상태
     */
    ADDED("ADDED"),
    /**
     * 관계없음
     */
    NONE("NONE"),
    /**
     * UNKNOWN
     */
    UNKNOWN("UNKNOWN");

    private final String relationName;

    PlusFriendRelation(final String relationName) {
        this.relationName = relationName;
    }

    public String getName() {
        return relationName;
    }

    public static PlusFriendRelation fromName(final String relationName) {
        for (PlusFriendRelation relation : PlusFriendRelation.values()) {
            if (relation.relationName.equals(relationName)) {
                return relation;
            }
        }
        return UNKNOWN;
    }
}
