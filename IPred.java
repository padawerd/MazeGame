interface IPred<T> {
    boolean apply(T t);
}

class IsCat implements IPred<String> {
    public boolean apply(String s) {
        return s.equals("cat");
    }
}

class IsCDE implements IPred<String> {
    public boolean apply(String s) {
        return s.equals("CDE");
    }
}