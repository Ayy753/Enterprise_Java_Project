package webd4201.sinkat;

import java.text.DecimalFormat;

/**
 * @author Thomas Sinka
 * @version 1.0
 * @since 01-21-2020
 */
public class Mark {
	/**
	 * Minimum possible GPA
	 */
	public final static float MINIMUM_GPA = 0.0f;
	/**
	 * Maximum possible GPA
	 */
	public final static float MAXIMUM_GPA = 5.0f;
	/**
	 * Decimal Formatter
	 */
	public final static DecimalFormat GPA = new DecimalFormat("##.#");
	
	/**
	 * This mark's corse code
	 */
	protected String courseCode;
	/**
	 * This mark's corse name
	 */
	protected String courseName;
	/**
	 * Result of GPA calculation
	 */
	protected int result;
	/**
	 * This course's GPA weighting
	 */
	protected float gpaWeighting;
	
	//	GETTERS AND SETTERS
	
	/**
	 * @return returns course code
	 */
	public String getCourseCode() {
		return courseCode;
	}
	/**
	 * Set the course code
	 * @param courseCode
	 * Course's code
	 */
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	/**
	 * @return returns course name
	 */
	public String getCourseName() {
		return courseName;
	}
	/**
	 * Set course name
	 * @param courseName
	 * Course's name
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	/**
	 * Returns GPA result
	 * @return 
	 * GPA result
	 */
	public int getResult() {
		return result;
	}
	/**
	 * Set GPA result
	 * @param result
	 * GPA calculation result
	 */
	public void setResult(int result) {
		this.result = result;
	}
	/**
	 * returns GPA weighting
	 * @return 
	 * GPA weighting
	 */
	public float getGpaWeighting() {
		return gpaWeighting;
	}
	/**
	 * sets GPA weighting
	 * @param gpaWeighting
	 * GPA weighting of course 
	 */
	public void setGpaWeighting(float gpaWeighting) {
		this.gpaWeighting = gpaWeighting;
	}
	
	//	CONSTRUCTORS
	/**
	 * An object that holds a class's information for a student
	 * @param courseCode - Course code for course
	 * @param courseName - Name of course
	 * @param result - Final result of course
	 * @param gpaWeighting - GPA weighting of course
	 */
	public Mark(String courseCode, String courseName, int result,
			float gpaWeighting) {
		
		setCourseCode(courseCode);
		setCourseName(courseName);
		setResult(result);
		setGpaWeighting(gpaWeighting);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String output = String.format("%-8s    %-35s    %d    %s" , getCourseCode(), getCourseName(), getResult(), 
				GPA.format(gpaWeighting));
		return output;
	}
}
