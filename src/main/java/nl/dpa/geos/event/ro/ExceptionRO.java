package nl.dpa.geos.event.ro;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionRO implements DpaEventReturnObject {

	private String exceptionMessage;
	private String exceptionInfo;

	public ExceptionRO(String exceptionMessage, String exceptionInfo) {
		super();
		this.exceptionMessage = exceptionMessage;
		this.exceptionInfo = exceptionInfo;
	}

}
