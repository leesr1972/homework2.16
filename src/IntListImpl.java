import exceptions.IndexOutBoundsException;
import exceptions.NotFoundException;

import java.util.Arrays;

public class IntListImpl implements IntList{
    private int[] array = new int[10];
    private int indexOfElement = 0;

    private void grow() {
        int[] tempArray = Arrays.copyOf(array, array.length);
        this.array = new int[indexOfElement + indexOfElement/2];
        array = Arrays.copyOf(tempArray, array.length);
    }

    @Override
    public int getSizeOfArray() {
        return array.length;
    }

    @Override
    public int add(int item) {
        if ((indexOfElement) == array.length) {
            grow();
        }
        array[indexOfElement] = item;
        indexOfElement++;
        return item;
    }

    @Override
    public int add(int index, int item) {
        if ((indexOfElement) == array.length) {
            grow();
        }
        if (index >= indexOfElement) {
            throw new IndexOutBoundsException("Введен некорректный индекс.");
        }
        for (int i = indexOfElement - 1; i >= index; i--) {
            array[i + 1] = array[i];
        }
        array[index] = item;
        indexOfElement++;
        return item;
    }

    @Override
    public int set(int index, int item) {
        if (index >= indexOfElement || index >= array.length) {
            throw new IndexOutBoundsException("Введен некорректный индекс.");
        }
        array[index] = item;
        return item;
    }

    @Override
    public int remove(int item) {
        int indexOfSearchableElement = indexOf(item);
        if (indexOfSearchableElement == -1) {
            throw new NotFoundException("Элемент не найден.");
        }
        for (int j = indexOfSearchableElement; j < indexOfElement - 1; j++) {
            array[j] = array[j + 1];
        }
        array[indexOfElement - 1] = 0;
        indexOfElement--;
        return item;
    }

    @Override
    public int removeByIndex(int index) {
        if (index >= indexOfElement) {
            throw new IndexOutBoundsException("Введен некорректный индекс.");
        }
        int removedElement = array[index];
        for (int i = index; i < indexOfElement - 1; i++) {
            array[i] = array[i + 1];
        }
        array[indexOfElement - 1] = 0;
        indexOfElement--;
        return removedElement;
    }

    @Override
    public boolean contains(int item) {
        quickSort(array, 0, indexOfElement);
        return contains(array, item);
    }

    @Override
    public int indexOf(int item) {
        int indexOfSearchableElement = -1;
        for (int i = 0; i < indexOfElement; i++) {
            if (array[i] == item) {
                indexOfSearchableElement = i;
                break;
            }
        }
        return indexOfSearchableElement;
    }

    @Override
    public int lastIndexOf(int item) {
        int indexOfSearchableElement = -1;
        for (int i = indexOfElement - 1; i >= 0; i--) {
            if (array[i] == item) {
                indexOfSearchableElement = i;
                break;
            }
        }
        return indexOfSearchableElement;
    }

    @Override
    public int get(int index) {
        if (index >= indexOfElement) {
            throw new IndexOutBoundsException("Введен некорректный индекс.");
        }
        return array[index];
    }

    @Override
    public boolean equals(IntList otherList) {
        boolean rezult = true;
        if (otherList.size() != indexOfElement) {
            rezult = false;
            return rezult;
        }
        for (int i = 0; i < indexOfElement; i++) {
            if (!(array[i] == otherList.get(i))) {
                rezult = false;
                return rezult;
            }
        }
        return rezult;
    }

    @Override
    public int size() {
        return indexOfElement;
    }

    @Override
    public boolean isEmpty() {
        return indexOfElement == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < indexOfElement; i++) {
            array[i] = 0;
        }
        indexOfElement = 0;
    }

    @Override
    public int[] toArray() {
        return Arrays.copyOf(array, indexOfElement);
    }

    private static void swapElements(int[] arr, int left, int right) {
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }

    private static void quickSort(int[] arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    private static int partition(int[] arr, int begin, int end) {
        int pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                swapElements(arr, i, j);
            }
        }

        swapElements(arr, i + 1, end);
        return i + 1;
    }

    private static boolean contains(int[] arr, int element) {
        int min = 0;
        int max = arr.length - 1;

        while (min <= max) {
            int mid = (min + max) / 2;

            if (element == arr[mid]) {
                return true;
            }

            if (element < arr[mid]) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return false;
    }
}
