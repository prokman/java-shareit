package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.dto.CommentDtoRequest;
import ru.practicum.shareit.item.dto.ItemCreateRequest;
import ru.practicum.shareit.item.dto.ItemUpdateRequest;

import java.util.Map;

@Service
@Slf4j
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory()).build());
    }


    public ResponseEntity<Object> getAllItem(long ownerId) {
        return get("", ownerId);
    }

    public ResponseEntity<Object> getItem(long itemId, long userId) {
        return get("/" + itemId, userId);
    }

    public ResponseEntity<Object> createItem(ItemCreateRequest itemCreateRequest, long ownerId) {
        return post("", ownerId, itemCreateRequest);
    }

    public ResponseEntity<Object> itemSearch(long ownerId, String text) {
        Map<String, Object> parameters = Map.of("text", text);
        log.info("itemClient myFlogs with text {}, ownerId={}, param={}", text, ownerId, parameters);
        return get("/search?text={text}", ownerId, parameters);
    }

    public ResponseEntity<Object> updateItem(ItemUpdateRequest itemUpdateRequest, long itemId, long ownerId) {
        return patch("/" + itemId, ownerId, itemUpdateRequest);
    }

    public ResponseEntity<Object> createComment(CommentDtoRequest commentDtoRequest, Long itemId, Long userId) {
        return post("/" + itemId + "/comment", userId, commentDtoRequest);
    }
}
