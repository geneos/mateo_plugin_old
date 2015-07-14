package ar.com.geneos.mrp.plugin.model;

public interface ModelValidator extends org.openXpertya.model.ModelValidator {

	/** Called before document is completed */
	public static final int TIMING_BEFORE_COMPLETE = 7;
	/** Called after document is prepared */
	public static final int TIMING_AFTER_PREPARE = 8;
	/** Called before document is close */
	public static final int TIMING_BEFORE_CLOSE = 3;
	/** Called before document is reactivate */
	public static final int TIMING_BEFORE_REACTIVATE = 4;
	/** Called after document is void */
	public static final int TIMING_AFTER_VOID = 10;
	/** Called before document is void */
	public static final int TIMING_BEFORE_VOID = 2;
	/** Called after document is closed */
	public static final int TIMING_AFTER_CLOSE = 11;
	/** Called after document is reactivated */
	public static final int TIMING_AFTER_REACTIVATE = 12;
}
