package surface;

import formula.*;
import formula.implementation.Formula;

public interface ISurface {
    /**	@return возвращает число точек в сетке по х - координате.
     */
    int getTCount();
    /** @return	возвращает число точек в сетке по y - координате.
     */
    int getUCount();
    /**проверяет введенные строки на корректность
     /**@return если одна из строк некоррректна, возвращает false
     */
    boolean isValid();
    /**	 @return Возвращает строку, содержащую Count T
     */
    String getStringCountT();
    /**	@return Возвращает строку, содержащую Count U
     */
    String getStringCountU();
    /**	@return возвращает объект класса IFormula, в котором хранится информация для построения
     * 	x, y, z формул - деревьев, а так же границ переменных.
     */
    IFormula getFormula();
    /**@return	возвращает координаты точки, лежащей на поверхности. Пара параметоров (i, j)
     *	соответствует узлу в сетке, по которой строится поверхность.
     */
    Point3D getValue(int i, int j);
    public void setMyFormula(IFormula formula);
}
