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
	public static final int TIMING_BEFORE_REVERSEACCRUAL = 6;
	public static final int TIMING_AFTER_REVERSEACCRUAL = 14;
	public static final int TIMING_BEFORE_REVERSECORRECT = 5;
	public static final int TIMING_AFTER_REVERSECORRECT = 13;

	/** Model Change Type New */
	public static final int TYPE_BEFORE_NEW = 1;
	public static final int CHANGETYPE_NEW = 1;
	public static final int	TYPE_AFTER_NEW = 4;

	/** Model Change Type Change */
	public static final int TYPE_BEFORE_CHANGE = 2;
	public static final int CHANGETYPE_CHANGE = 2;
	public static final int	TYPE_AFTER_CHANGE = 5;

	/** Model Change Type Delete */
	public static final int TYPE_BEFORE_DELETE = 3;
	public static final int CHANGETYPE_DELETE = 3;
	public static final int	TYPE_AFTER_DELETE = 6;
}
