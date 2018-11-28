package base;

import java.util.Comparator;
import java.util.function.Function;

public class NameComparator<T> implements Comparator<T>{

	private final Function<T, String> func;
	
	public NameComparator(Function<T, String> func) {
		this.func = func;
	}
	
	@Override
	public int compare(T o1, T o2) {
		String s1 = o1 == null ? null : func.apply(o1);
		String s2 = o2 == null ? null : func.apply(o2);
		return compare(s1, s2);
	}

	private static final char[] Numbers = {'一', '二', '三', '四', '五', '六', '七', '八', '九', '十'};
	
	private int compare(String s1, String s2) {
		if(s1 == s2) {
			return 0;
		}
		if(s1 == null) {
			return 1;
		}
		if(s2 == null) {
			return -1;
		}
		int i=0, j=0;
		for(; i<s1.length() && j<s2.length(); i++, j++) {
			int ch1 = s1.charAt(i);
			int ch2 = s2.charAt(j);
			
			if(ch1 == ch2) {
				continue;
			}
			
			int compareNumber = compareFix(Numbers, ch1, ch2);
			if(compareNumber != 0) {
				return compareNumber;
			}
			return ch1 - ch2;
		}
		if(i < s1.length()) {
			return -1;
		}
		if(j < s2.length()) {
			return 1;
		}
		return 0;
	}

	private static int compareFix(char[] ch, int a, int b) {
		int t1 = -1, t2 = -1;
		int flag = 0;
		for(int i=0; i<ch.length && flag < 2; i++) {
			char c = ch[i];
			if(c == a) {
				t1 = i;
				flag++;
			}
			if(c == b) {
				t2 = i;
				flag++;
			}
		}
		return t1 - t2;
	}
	
}
