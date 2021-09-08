package mystack;

public class SeqList<T> extends Object{
	protected int n;				//顺序表中元素个数
	protected Object[] element;		//对象数组存储顺序表的元素
	private static final int MIN = 16;//静态常量，表示数组的最小容量

	public SeqList(int length) {	//构造容量为length的空表
		if(length < MIN)
			length = MIN;
		this.element = new Object[length];
		this.n = 0;
	}
	public SeqList() {				//构造默认容量的空表
		this(MIN);
	}
	public void clear() {
		this.n = 0;
	}
	/**将元素插入到表中位置为num处*/
	public void insert(T x, int num) {
		Object[] step = this.element;
		if(this.n == this.element.length) {
			this.element = new Object[step.length * 2];
			for(int i = 0; i < num; i++)
				this.element[i] = step[i];
		}
		for(int i = this.n - 1; i > num; i--)
			this.element[i + 1] = step[i];
		this.element[num] = x;
		this.n++;
	}
	/**在表尾插入元素*/
	public void insert(T x) {
		this.insert(x, this.n);
	}
	/**删除表中位置为i的元素，并返回其值*/
	public T remove(int i) {
		if(i >= 0 && i < this.n) {
			T x = (T)this.element[i];
			for(int j = i; j < this.n - 1; j++)
				this.element[j] = this.element[j + 1];
			this.element[this.n - 1] = null;
			this.n--;
			return x;
		}
		return null;
	}
	/**返回表中位置i的元素的值*/
	public T get(int i) {
		if(i >= 0 && i < this.n)
			return (T)this.element[i];
		return null;
	}
	/**返回表中元素个数*/
	public int size() {
		return this.n;
	}
}
