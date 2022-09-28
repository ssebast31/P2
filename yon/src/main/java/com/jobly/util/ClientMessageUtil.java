package com.jobly.util;

import com.jobly.models.ClientMessage;

public class ClientMessageUtil {
	public static final ClientMessage CREATION_SUCCESSFUL = new ClientMessage("CREATION SUCCESSFUL");
	public static final ClientMessage CREATION_FAILED = new ClientMessage("SOMETHING WENT WRONG DURING CREATION");
	public static final ClientMessage UPDATE_SUCCESSFUL = new ClientMessage("UPDATE SUCCESSFUL");
	public static final ClientMessage UPDATE_FAILED = new ClientMessage("SOMETHING WENT WRONG DURING UPDATE");
	public static final ClientMessage DELETION_SUCCESSFUL = new ClientMessage("DELETION SUCCESSFUL");
	public static final ClientMessage DELETION_FAILED = new ClientMessage("SOMETHING WENT WRONG DURING DELETION");
	public static final ClientMessage ENTITY_ALREADY_EXISTS = new ClientMessage("AN ENTITY MATCHING THIS ONE ALREADY EXISTS.");
	public static final ClientMessage USERNAME_ALREADY_EXISTS = new ClientMessage("THAT USERNAME IS ALREADY TAKEN");
	public static final ClientMessage EMAIL_ALREADY_EXISTS = new ClientMessage("THAT EMAIL IS ALREADY TAKEN");
	public static final ClientMessage COMPANY_NAME_ALREADY_EXISTS = new ClientMessage("THAT NAME IS ALREADY TAKEN");
}