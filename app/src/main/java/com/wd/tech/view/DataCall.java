package com.wd.tech.view;

import com.wd.tech.exception.ApiException;


public interface DataCall<T> {
    void success(T result);
    void fail(ApiException e);
}
