package com.mycompany.sentry.entity;

import java.io.Serializable;
import java.util.Objects;

public class LessonVocabularyId implements Serializable {
    private Integer lessonID;
    private Integer vocabID;

    public LessonVocabularyId() {
    }

    public LessonVocabularyId(Integer lessonID, Integer vocabID) {
        this.lessonID = lessonID;
        this.vocabID = vocabID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LessonVocabularyId that = (LessonVocabularyId) o;
        return Objects.equals(lessonID, that.lessonID) &&
               Objects.equals(vocabID, that.vocabID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonID, vocabID);
    }
}




