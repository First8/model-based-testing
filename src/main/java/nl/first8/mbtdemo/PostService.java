package nl.first8.mbtdemo;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@Service
@RequiredArgsConstructor
public class PostService {
	@Delegate
	private final PostRepository repository;
}
