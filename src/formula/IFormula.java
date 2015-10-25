package formula;

public interface IFormula {
    /**	 @return Возвращает строку, содержащую X формулу
     */
    String getXFormula();
    /**	@return Возвращает строку, содержащую Y формулу
     */
    String getYFormula();
    /**	@return Возвращает строку, содержащую Z формулу
     */
    String getZFormula();

    /** @return	Возвращает строку, содержащую начальную границу переменной T
     */
    String getStringStartT();
    /** @return	Возвращает строку, содержащую конечную границу переменной T
     */
    String getStringEndT();
    /** @return	Возвращает строку, содержащую начфльную границу переменной U
     */
    String getStringStartU();
    /** @return	Возвращает строку, содержащую конечную границу переменной U
     */
    String getStringEndU();
    public void setMyStringX(String myStringX);
    public void setMyStringY(String myStringY);
    public void setMyStringZ(String myStringZ);

    //void setXFormula(String str);
    //void setYFormula(String str);
    //void setZFormula(String str);

    /** @return	Возвращает число, равное начальной границе переменной t
     */
    double getStartT();
    /** @return	Возвращает число, равное конечной границе переменной t
     */
    double getEndT();
    /** @return	Возвращает число, равное начальной границе переменной u
     */
    double getStartU();
    /**	 @return Возвращает число, равное конечной границе переменной u
     */
    double getEndU();
	
	/*
	void setStartT(double startT);
	void setEndT(double endT);
	void setStartU(double startU);
	void setEndU(double endU);
	*/

    /**	проверяет все строки на корректоность
     * @return если одна из строк не корректна, метод возвращает false
     */
    boolean isValid();
    /**
     * @return возвращает 3-х мерную точку, вычисленную по заранее заданным x,y,z
     *  формулам при конкретных параметрах u,t
     */
    Point3D evaluate(double u, double t);
}
