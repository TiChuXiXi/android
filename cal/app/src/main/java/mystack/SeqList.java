package mystack;

public class SeqList<T> extends Object{
	protected int n;				//˳�����Ԫ�ظ���
	protected Object[] element;		//��������洢˳����Ԫ��
	private static final int MIN = 16;//��̬��������ʾ�������С����

	public SeqList(int length) {	//��������Ϊlength�Ŀձ�
		if(length < MIN)
			length = MIN;
		this.element = new Object[length];
		this.n = 0;
	}
	public SeqList() {				//����Ĭ�������Ŀձ�
		this(MIN);
	}
	public void clear() {
		this.n = 0;
	}
	/**��Ԫ�ز��뵽����λ��Ϊnum��*/
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
	/**�ڱ�β����Ԫ��*/
	public void insert(T x) {
		this.insert(x, this.n);
	}
	/**ɾ������λ��Ϊi��Ԫ�أ���������ֵ*/
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
	/**���ر���λ��i��Ԫ�ص�ֵ*/
	public T get(int i) {
		if(i >= 0 && i < this.n)
			return (T)this.element[i];
		return null;
	}
	/**���ر���Ԫ�ظ���*/
	public int size() {
		return this.n;
	}
}
