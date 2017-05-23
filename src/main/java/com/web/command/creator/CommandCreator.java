package com.web.command.creator;

import java.util.HashMap;
import java.util.Map;
import com.web.command.Command;
import com.web.command.impl.AddBookInDB;
import com.web.command.impl.ChangePageLanguage;
import com.web.command.impl.ChangeUserLanguage;
import com.web.command.impl.GetBook;
import com.web.command.impl.GetUserSignInInfo;
import com.web.command.impl.GetUserSignUpInfo;
import com.web.command.impl.LogOut;
import com.web.command.impl.RemoveBook;
import com.web.command.impl.UpdateBookInfo;
import com.web.command.impl.GetBookInfo;

public class CommandCreator {

	private static volatile CommandCreator creator;
	private static final String GET_SIGN_IN_INFO = "SignIn";
	private static final String GET_SIGN_UP_INFO = "SignUp";
	private static final String GET_BOOK = "getBook";
	private static final String CHANGE_LANGUAGE = "changeLanguage";
	private static final String CHANGE_PAGE_LANGUAGE = "changePageLang";
	private static final String REMOVE_BOOK = "removeBook";
	private static final String UPDATE_BOOK_INFO = "updateBookInfo";
	private static final String GET_BOOK_INFO = "getBookInfo";
	private static final String ADD_BOOK = "addBook";

	private static final String LOG_OUT = "logOut";
	private Map<String, Command> commandCreator = new HashMap<String, Command>();

	private CommandCreator() {
		commandCreator.put(GET_SIGN_IN_INFO, new GetUserSignInInfo());
		commandCreator.put(GET_SIGN_UP_INFO, new GetUserSignUpInfo());
		commandCreator.put(LOG_OUT, new LogOut());
		commandCreator.put(GET_BOOK, new GetBook());
		commandCreator.put(CHANGE_LANGUAGE, new ChangeUserLanguage());
		commandCreator.put(CHANGE_PAGE_LANGUAGE, new ChangePageLanguage());
		commandCreator.put(REMOVE_BOOK, new RemoveBook());
		commandCreator.put(UPDATE_BOOK_INFO, new UpdateBookInfo());
		commandCreator.put(GET_BOOK_INFO, new GetBookInfo());
		commandCreator.put(ADD_BOOK, new AddBookInDB());
	}

	public static CommandCreator getCommandCreator() {
		if (creator == null) {
			synchronized (CommandCreator.class) {
				if (creator == null) {
					creator = new CommandCreator();
				}
			}
		}
		return creator;
	}

	public Command getCommand(String command) {
		return commandCreator.get(command);
	}
}
