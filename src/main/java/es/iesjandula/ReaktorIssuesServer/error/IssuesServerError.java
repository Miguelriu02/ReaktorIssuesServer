package es.iesjandula.ReaktorIssuesServer.error;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IssuesServerError extends Exception
{

	/**
	 * This is the auto-generated ID
	 */
	private static final long serialVersionUID = 8144321039123138732L;

	private int id;

	private String message;

	private Exception exception;

	public IssuesServerError(int id, String message, Exception exception)
	{
		super();
		this.id = id;
		this.message = message;
		this.exception = exception;
	}

	public IssuesServerError(int id, String message)
	{
		super();
		this.id = id;
		this.message = message;
	}

	public Map<String, String> getMapError()
	{
		Map<String, String> mapError = new HashMap<String, String>();

		mapError.put("id", "" + id);
		mapError.put("message", message);

		if (this.exception != null)
		{
			String stacktrace = ExceptionUtils.getStackTrace(this.exception);
			mapError.put("exception", stacktrace);
		}

		return mapError;
	}

}
