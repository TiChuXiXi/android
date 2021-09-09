package mystack;

public interface Stack<T> {
	/**清空栈内元素*/
	public void clear();
	/**元素入栈*/
	public void push(T x);
	/**元素出栈*/
	public T pop();
	/**返回栈顶元素，不出栈*/
	public T peek();
	/**判断栈是否为空*/
	public boolean isEmpty();
}
