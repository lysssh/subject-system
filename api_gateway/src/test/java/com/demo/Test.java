package com.demo;


import java.util.*;

class Process {
    public String name;
    public int needSpace;
    public int needTime;
    public int priority;
    public int order;

    public Process(String name, int needSpace, int needTime, int priority, int order) {
        this.name = name;
        this.needSpace = needSpace;
        this.needTime = needTime;
        this.priority = priority;
        this.order = order;
    }
}


public class Test {
    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 3; i++) {
            String name = scanner.next();
            int space = scanner.nextInt();
            int time = scanner.nextInt();
            int priority = scanner.nextInt();
            Process process = new Process(name, space, time, priority, i);
            processes.add(process);
        }
        processes.sort(new Comparator<Process>() {
            @Override
            public int compare(Process o1, Process o2) {
                return o2.priority - o1.priority;
            }
        });
        List<Process> list = new ArrayList<>();
        List<Process> finalList = new ArrayList<>();
        list.add(processes.get(0));
        int space = 100 - processes.get(0).needSpace;
        processes.remove(0);
        while (finalList.size() != 3) {
            for (int i = 0; i < processes.size(); i++) {
                if (space >= processes.get(i).needSpace) {
                    list.add(processes.get(i));
                    space = space - processes.get(i).needSpace;
                    processes.remove(i);
                    i--;
                }
            }
            list.sort(new Comparator<Process>() {
                @Override
                public int compare(Process o1, Process o2) {
                    if (o1.needTime == o2.needTime) {
                        return o1.order - o2.order;
                    }
                    return o1.needTime - o2.needTime;
                }
            });

            finalList.add(list.get(0));
            space += list.get(0).needSpace;
            list.remove(0);
        }

        for (int i = 0; i < finalList.size(); i++) {
            if (i == 0) {
                System.out.print(finalList.get(i).name);
            } else {
                System.out.print(" " + finalList.get(i).name);
            }
        }
    }

}
