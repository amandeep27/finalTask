package com.web.service;

import java.util.List;
import com.web.domain.BookInfo;
import com.web.dto.GetBookInfo;
import com.web.service.exception.ServiceException;

public interface BookService {

	List<BookInfo> getBookInfo(GetBookInfo bookInfo) throws ServiceException;

}
