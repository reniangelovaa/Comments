package bg.softuni.comments;

import bg.softuni.comments.model.Comment;
import bg.softuni.comments.model.dto.CommentDTO;

public class CommentMapper {
    public static Comment toEntity(CommentDTO dto) {
        Comment comment = new Comment();
        comment.setBookId(dto.getBookId());
        comment.setAuthor(dto.getAuthor());
        comment.setText(dto.getText());
        return comment;
    }

    public static CommentDTO toDto(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setBookId(comment.getBookId());
        dto.setAuthor(comment.getAuthor());
        dto.setText(comment.getText());
        return dto;
    }
}
