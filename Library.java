public class Library {
    TrieTree people_database, books_database;

    Library() {
        people_database = new TrieTree(26);
        books_database = new TrieTree(26);
    }

    void arrive(String personName, long time){
        TrieTree.Node person = people_database.insert(personName);
        person.isInLib = true;
        if(person.in_time == null) {
            person.in_time = new LongList();
        }
        person.in_time.add(time);
    }

    void exit(String personName, long time){
        TrieTree.Node person = people_database.insert(personName);
        person.isInLib = false;
        if(person.out_time == null) {
            person.out_time = new LongList();
        }
        if(person.sum_time == null) {
            person.sum_time = new LongList();
        }
        if(person.in_time.size() <= person.out_time.size()){
            person.in_time.add(0);
        }
        person.out_time.add(time);
        person.sum_time.add((person.out_time.getLast() - person.in_time.getLast()) + person.sum_time.getLast());
    }

    boolean isInLib(String personName){
        TrieTree.Node person = people_database.search(personName);
        if(person == null)
            return false;
        return person.isInLib;
    }

    void borrowBook(String personName, String bookName){
        TrieTree.Node book = books_database.search(bookName);
        TrieTree.Node person = people_database.search(personName);
        if(book != null && person != null) {
            if(book.count > 0) {
                if(person.sub_feature == null)
                    person.sub_feature = new TrieTree(26);
                person.sub_feature.insert(bookName);
                person.sub_feature_count++;
                if(book.sub_feature == null)
                    book.sub_feature = new TrieTree(26);
                book.sub_feature.insert(personName);
                book.sub_feature_count++;
                book.count--;
            }
        }
    }

    void returnBook(String personName, String bookName){
        TrieTree.Node person = people_database.search(personName);
        TrieTree.Node book = books_database.search(bookName);
        if(person != null) {
            if(book == null){
                addNewBook(bookName, 0);
            }
            if(person.sub_feature == null)
                person.sub_feature = new TrieTree(26);
            if(person.sub_feature.search(bookName) != null) {
                person.sub_feature.remove(bookName);
                person.sub_feature_count--;
            }
            if(book.sub_feature == null)
                book.sub_feature = new TrieTree(26);
            if(book.sub_feature.search(personName) != null) {
                book.sub_feature.remove(personName);
                book.sub_feature_count--;
                book.count++;
            }
        }
    }

    int binarySearch(LongList list, long time){
        int low = 0, high = list.size() - 1, mid = 0;
        while(low <= high){
            mid = (low + high) / 2;
            if(list.get(mid) == time)
                return mid;
            else if(list.get(mid) < time)
                low = mid + 1;
            else
                high = mid - 1;
        }
        return mid;
    }

    long totalTimeInLib(String personName, long startTime, long endTime) {
        TrieTree.Node person = people_database.insert(personName);
        if (person.in_time == null)
            person.in_time = new LongList();
        if (person.out_time == null)
            person.out_time = new LongList();
        if (person.sum_time == null)
            person.sum_time = new LongList();

        LongList in = person.in_time;
        LongList out = person.out_time;
        LongList sum = person.sum_time;

        int in_index = binarySearch(in, startTime);
        int out_index = binarySearch(out, endTime);

        if(in_index > 0 && startTime < in.get(in_index)){
            in_index--;
        }
        if(out_index > 0 && endTime < out.get(out_index)){
            out_index--;
        }
        if (in_index < out.size() - 1 && startTime > out.get(in_index)) {
            in_index++;
        }
        if(out_index < out.size() - 1 && out_index < in.size() - 1 && endTime > in.get(out_index + 1)) {
            out_index++;
        }

        if(endTime < in.get(0)) {
            return 0;
        }

        if(in.size() > out.size() && in_index == in.size() - 1) {
            if(startTime > out.getLast()) {
                return endTime - startTime;
            }
            else{
                return endTime - in.getLast();
            }
        }

        if(in_index == in.size() - 1 && out.getLast() <= startTime) {
            return 0;
        }

        long ans = sum.get(out_index) - (in_index > 0 ? sum.get(in_index - 1) : 0);
        if (in.get(in_index) < startTime)
            ans -= startTime - in.get(in_index);
        if (out.get(out_index) > endTime)
            ans -= out.get(out_index) - endTime;
        if(in.size() > out.size() && endTime > in.getLast()) {
            ans += endTime - in.getLast();
        }
        return ans;
    }

    void addNewBook(String bookName, long count){
        TrieTree.Node book = books_database.insert(bookName);
        book.count += count;
    }

    void shouldBringBook(String bookName, String personName){
        if(people_database.insert(personName).isInLib){
            books_database.insert(bookName);
            borrowBook(personName, bookName);
        }
    }

    void allPersonCurrentBooks(String personName){
        TrieTree.Node person = people_database.insert(personName);
        if(person.sub_feature_count == 0){
            System.out.println("empty");
        }
        else {
            person.sub_feature.print_all_children();
            System.out.println();
        }
    }

    void allPersonsHaveThisBook(String bookName){
        TrieTree.Node book = books_database.insert(bookName);
        if(book.sub_feature_count == 0){
            System.out.println("empty");
        }
        else {
            book.sub_feature.print_all_children();
            System.out.println();
        }
    }
}