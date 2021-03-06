package com.kodilla.collectionmanagerfrontend.bookscollectionview;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCommentOnBook {
    private Long commentId;
    private String date;
    private String content;
    private String bookId;
}
