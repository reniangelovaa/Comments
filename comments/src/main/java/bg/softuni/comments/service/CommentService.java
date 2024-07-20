package bg.softuni.comments.service;

import bg.softuni.comments.mapper.CommentMapper;
import bg.softuni.comments.model.Comment;
import bg.softuni.comments.model.dto.CommentDTO;
import bg.softuni.comments.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment addComment(CommentDTO commentDTO) {
        Comment comment = CommentMapper.toEntity(commentDTO);
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public List<Comment> getCommentsByBookId(Long bookId) {
        return commentRepository.findByBookId(bookId);
    }
}
