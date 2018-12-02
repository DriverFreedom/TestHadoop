package day07.movie.top2;

import java.util.Comparator;
import java.util.TreeSet;

import day07.movie.top1.MovieBean;

public class TestTreeSet {

	public static void main(String[] args) {
		TreeSet<MovieBean> tree = new TreeSet<>(new Comparator<MovieBean>() {

			@Override
			public int compare(MovieBean o1, MovieBean o2) {
				if(o1.getRate() - o2.getRate() ==0){
					return o1.getMovie().compareTo(o2.getMovie());
				}else{
					return  o1.getRate() - o2.getRate();
				}
			
			}
		});
		
		MovieBean b1 = new MovieBean();
		MovieBean b2 = new MovieBean();
		MovieBean b3 = new MovieBean();
		MovieBean b4 = new MovieBean();
		
		b1.set("100", 5, "87909", "1");
		b2.set("101", 4, "87909", "1");
		b3.set("102", 5, "87909", "1");
		b4.set("103", 3, "87909", "1");
		tree.add(b1);
		tree.add(b2);
		tree.add(b3);
		tree.add(b4);
		System.out.println(tree.size());
		System.out.println(tree.first());
		System.out.println(tree.remove(tree.first()));
		for (MovieBean movieBean : tree) {
			System.out.println(movieBean);
		}
		
	}
}
