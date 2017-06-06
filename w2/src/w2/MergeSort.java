package w2;
import java.util.Comparator;
import java.util.Random;

public class MergeSort
{
    static boolean less(Object x, Object y, Comparator c)
    {
        return (c.compare(x, y) < 0);
    }

    static boolean isSorted(Object [] a, Comparator c)
    {
        for (int i = 0; i < a.length-1; i++)
        {
            if (less(a[i+1], a[i], c))
            {
                return false;
            }
        }
        return true;
    }

    static void merge(Object[] a, Object[] tmp, int lo, int mid, int hi, Comparator c)
    {
        for (int i = lo; i <= hi; i++)
        {
            tmp[i] = a[i];
        }
        int i = lo;
        int j = mid+1;
        for (int k = lo; k <= hi; k++ )
        {
            if (i > mid)
            {
                a[k]=tmp[j++];
            }
            else if (j > hi)
            {
                a[k]=tmp[i++];
            }
            else if (less(tmp[j], tmp[i], c)) //stable sort
            {
                a[k] = tmp[j++];
            }
            else {
                a[k] = tmp[i++];
            }
        }
    }

    private static void sort(Object[] a, Object[] tmp, int lo, int hi, Comparator c)
    {
        if (hi <= lo) return;

        int mid = lo + (hi-lo)/2;
        sort(a,tmp,lo,mid, c);
        sort(a,tmp,mid+1,hi, c);
        merge(a,tmp,lo,mid,hi,c);
    }

    public static void sort(Object[] a, Comparator c)
    {
        Object[] tmp = new Object[a.length];
        sort(a, tmp, 0,a.length-1,c);
    }

    //TDD Test driven development
    public static void main(String[] args) {
        Random rand = new Random();
        int trials = 100;

        for (int t = 0; t < trials; t++) {
            int sz = rand.nextInt(100) + 1;
            Student[] students = new Student[sz];
            for (int i = 0; i < sz; i++) {
                String name = "Student " + rand.nextInt(sz);
                int grade = rand.nextInt(sz);
                students[i] = new Student(name, grade);
            }

            sort(students, Student.BY_NAME);
            assert isSorted(students, Student.BY_NAME);

            sort(students, Student.BY_GRADE);
            assert isSorted(students, Student.BY_GRADE);
        }

        Student[] students = {new Student("A", 8), new Student("A", 9)};
        sort(students, Student.BY_GRADE);
        sort(students, Student.BY_NAME);
        for (Student s : students) {
            System.out.printf("%s -> ", s.toString());
        }

    }

}

class Student
{
    public static final Comparator BY_NAME = new ByName();
    public static final Comparator BY_GRADE = new ByGrade();

    String name;
    int grade;

    public String toString()
    {
        return name + ": " + grade;
    }

    public Student(String name, int grade)
    {
        this.name = name;
        this.grade = grade;
    }

    private static class ByName implements Comparator<Student>
    {
        @Override
        public int compare(Student x, Student y)
        {
            if (x == null || y == null)
            {
                throw new NullPointerException();
            }
            return x.name.compareTo(y.name);
        }
    }

    private static class ByGrade implements Comparator<Student>
    {
        @Override
        public int compare(Student x, Student y)
        {
            if (x == null || y == null)
            {
                throw new NullPointerException();
            }
            return x.grade - y.grade;
        }
    }

}