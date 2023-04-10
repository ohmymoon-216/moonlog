package com.ohmymoon.moonlog.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostSearch {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    public long getOffset() {
        return (long) (Math.max(1, page)-1) * size;
    }


}
