public class BinarySearch{
    public static boolean binarySearch(int[] data, int target, int low, int high){
        if(low > high){
            return false;
        }else{
            int mid = (low+high)/2;
            if(target == data[mid]){
                return true;
            }else if(target <data[mid]){
                return binarySearch(data,target,low,mid-1);
            }else{
                return binarySearch(data,target,mid+1, high);
            }
        }
    }
    public static void main(String[] args){
        int[] data = {1,3,5,6,7,11,24,56,78};
        boolean flag = binarySearch(data,11,0,data.length-1);
        if(flag){
            System.out.println("true");
        }else{
            System.out.println("false");
        }
    }
}