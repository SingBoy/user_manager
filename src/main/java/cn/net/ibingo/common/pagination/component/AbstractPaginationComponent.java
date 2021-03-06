package cn.net.ibingo.common.pagination.component;

import java.io.IOException;
import java.io.Writer;

import cn.net.ibingo.common.pagination.model.PaginationList;
import cn.net.ibingo.common.pagination.tag.PaginationTag;

/**
 * @author LOMI
 *
 */
public abstract class AbstractPaginationComponent implements PaginationComponent {

	protected PaginationTag paginationTag = null;

	protected PaginationList<?> paginatedList;

	protected String adjustUrl(String url) {
		if (url == null) return "";

		//trim jsessionid
		int beginPos = url.indexOf(';');
		if(beginPos >= 0) {
			int endPos = url.indexOf('?');
			if(beginPos > endPos) endPos = url.length();
			url = url.substring(endPos);
		}

		if (url.indexOf('?') < 0) {
			url = url + "?";
		} else {
			url = url + "&";
		}

		return url;
	}

	public AbstractPaginationComponent(PaginationTag pagerTag, PaginationList<?> paginatedList) {
		this.paginationTag = pagerTag;
		this.paginatedList = paginatedList;
	}

	@Override
	public abstract void render(Writer writer) throws IOException;

}
