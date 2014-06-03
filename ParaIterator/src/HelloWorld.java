import pi.*;//####[2]####
import pt.runtime.TaskIDGroup;//####[3]####
import java.util.Collection;//####[5]####
import java.util.List;//####[6]####
//####[6]####
//-- ParaTask related imports//####[6]####
import pt.runtime.*;//####[6]####
import java.util.concurrent.ExecutionException;//####[6]####
import java.util.concurrent.locks.*;//####[6]####
import java.lang.reflect.*;//####[6]####
import pt.runtime.GuiThread;//####[6]####
import java.util.concurrent.BlockingQueue;//####[6]####
import java.util.ArrayList;//####[6]####
import java.util.List;//####[6]####
//####[6]####
public class HelloWorld {//####[8]####
    static{ParaTask.init();}//####[8]####
    /*  ParaTask helper method to access private/protected slots *///####[8]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[8]####
        if (m.getParameterTypes().length == 0)//####[8]####
            m.invoke(instance);//####[8]####
        else if ((m.getParameterTypes().length == 1))//####[8]####
            m.invoke(instance, arg);//####[8]####
        else //####[8]####
            m.invoke(instance, arg, interResult);//####[8]####
    }//####[8]####
//####[10]####
    private void notifyFunc() {//####[10]####
        System.out.println("in notifyFunc");//####[11]####
    }//####[12]####
//####[14]####
    public static void main(String[] args) {//####[14]####
        List<String> list = new ArrayList<String>();//####[15]####
        for (Integer j = 1; j <= 50; j++) //####[16]####
        {//####[16]####
            list.add(j.toString());//####[17]####
        }//####[18]####
        System.out.println("Created list");//####[19]####
        Collection<String> elements = list;//####[20]####
        int numOfTasks = 5;//####[22]####
        ParIterator<String> pi = TaskAwareParIteratorFactory.createParIterator(elements, 4, numOfTasks, ParIterator.Schedule.TASKAWARE, 3);//####[24]####
        TaskIDGroup g = new TaskIDGroup(numOfTasks);//####[26]####
        for (int i = 0; i < numOfTasks; i++) //####[28]####
        {//####[28]####
            TaskID id = parataskTest(pi, i);//####[29]####
            g.add(id);//####[30]####
        }//####[31]####
        pi.notifyAll();//####[32]####
        System.out.println("Like" + g.groupSize());//####[35]####
        System.out.println("Worked");//####[36]####
    }//####[38]####
//####[40]####
    private static void hello(String name) {//####[40]####
        System.out.println("Hello from " + name);//####[41]####
    }//####[42]####
//####[44]####
    private static volatile Method __pt__parataskTest_ParIteratorString_int_method = null;//####[44]####
    private synchronized static void __pt__parataskTest_ParIteratorString_int_ensureMethodVarSet() {//####[44]####
        if (__pt__parataskTest_ParIteratorString_int_method == null) {//####[44]####
            try {//####[44]####
                __pt__parataskTest_ParIteratorString_int_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__parataskTest", new Class[] {//####[44]####
                    ParIterator.class, int.class//####[44]####
                });//####[44]####
            } catch (Exception e) {//####[44]####
                e.printStackTrace();//####[44]####
            }//####[44]####
        }//####[44]####
    }//####[44]####
    public static TaskID<Void> parataskTest(ParIterator<String> pi, int i) {//####[44]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[44]####
        return parataskTest(pi, i, new TaskInfo());//####[44]####
    }//####[44]####
    public static TaskID<Void> parataskTest(ParIterator<String> pi, int i, TaskInfo taskinfo) {//####[44]####
        // ensure Method variable is set//####[44]####
        if (__pt__parataskTest_ParIteratorString_int_method == null) {//####[44]####
            __pt__parataskTest_ParIteratorString_int_ensureMethodVarSet();//####[44]####
        }//####[44]####
        taskinfo.setParameters(pi, i);//####[44]####
        taskinfo.setMethod(__pt__parataskTest_ParIteratorString_int_method);//####[44]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[44]####
    }//####[44]####
    public static TaskID<Void> parataskTest(TaskID<ParIterator<String>> pi, int i) {//####[44]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[44]####
        return parataskTest(pi, i, new TaskInfo());//####[44]####
    }//####[44]####
    public static TaskID<Void> parataskTest(TaskID<ParIterator<String>> pi, int i, TaskInfo taskinfo) {//####[44]####
        // ensure Method variable is set//####[44]####
        if (__pt__parataskTest_ParIteratorString_int_method == null) {//####[44]####
            __pt__parataskTest_ParIteratorString_int_ensureMethodVarSet();//####[44]####
        }//####[44]####
        taskinfo.setTaskIdArgIndexes(0);//####[44]####
        taskinfo.addDependsOn(pi);//####[44]####
        taskinfo.setParameters(pi, i);//####[44]####
        taskinfo.setMethod(__pt__parataskTest_ParIteratorString_int_method);//####[44]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[44]####
    }//####[44]####
    public static TaskID<Void> parataskTest(BlockingQueue<ParIterator<String>> pi, int i) {//####[44]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[44]####
        return parataskTest(pi, i, new TaskInfo());//####[44]####
    }//####[44]####
    public static TaskID<Void> parataskTest(BlockingQueue<ParIterator<String>> pi, int i, TaskInfo taskinfo) {//####[44]####
        // ensure Method variable is set//####[44]####
        if (__pt__parataskTest_ParIteratorString_int_method == null) {//####[44]####
            __pt__parataskTest_ParIteratorString_int_ensureMethodVarSet();//####[44]####
        }//####[44]####
        taskinfo.setQueueArgIndexes(0);//####[44]####
        taskinfo.setIsPipeline(true);//####[44]####
        taskinfo.setParameters(pi, i);//####[44]####
        taskinfo.setMethod(__pt__parataskTest_ParIteratorString_int_method);//####[44]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[44]####
    }//####[44]####
    public static TaskID<Void> parataskTest(ParIterator<String> pi, TaskID<Integer> i) {//####[44]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[44]####
        return parataskTest(pi, i, new TaskInfo());//####[44]####
    }//####[44]####
    public static TaskID<Void> parataskTest(ParIterator<String> pi, TaskID<Integer> i, TaskInfo taskinfo) {//####[44]####
        // ensure Method variable is set//####[44]####
        if (__pt__parataskTest_ParIteratorString_int_method == null) {//####[44]####
            __pt__parataskTest_ParIteratorString_int_ensureMethodVarSet();//####[44]####
        }//####[44]####
        taskinfo.setTaskIdArgIndexes(1);//####[44]####
        taskinfo.addDependsOn(i);//####[44]####
        taskinfo.setParameters(pi, i);//####[44]####
        taskinfo.setMethod(__pt__parataskTest_ParIteratorString_int_method);//####[44]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[44]####
    }//####[44]####
    public static TaskID<Void> parataskTest(TaskID<ParIterator<String>> pi, TaskID<Integer> i) {//####[44]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[44]####
        return parataskTest(pi, i, new TaskInfo());//####[44]####
    }//####[44]####
    public static TaskID<Void> parataskTest(TaskID<ParIterator<String>> pi, TaskID<Integer> i, TaskInfo taskinfo) {//####[44]####
        // ensure Method variable is set//####[44]####
        if (__pt__parataskTest_ParIteratorString_int_method == null) {//####[44]####
            __pt__parataskTest_ParIteratorString_int_ensureMethodVarSet();//####[44]####
        }//####[44]####
        taskinfo.setTaskIdArgIndexes(0, 1);//####[44]####
        taskinfo.addDependsOn(pi);//####[44]####
        taskinfo.addDependsOn(i);//####[44]####
        taskinfo.setParameters(pi, i);//####[44]####
        taskinfo.setMethod(__pt__parataskTest_ParIteratorString_int_method);//####[44]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[44]####
    }//####[44]####
    public static TaskID<Void> parataskTest(BlockingQueue<ParIterator<String>> pi, TaskID<Integer> i) {//####[44]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[44]####
        return parataskTest(pi, i, new TaskInfo());//####[44]####
    }//####[44]####
    public static TaskID<Void> parataskTest(BlockingQueue<ParIterator<String>> pi, TaskID<Integer> i, TaskInfo taskinfo) {//####[44]####
        // ensure Method variable is set//####[44]####
        if (__pt__parataskTest_ParIteratorString_int_method == null) {//####[44]####
            __pt__parataskTest_ParIteratorString_int_ensureMethodVarSet();//####[44]####
        }//####[44]####
        taskinfo.setQueueArgIndexes(0);//####[44]####
        taskinfo.setIsPipeline(true);//####[44]####
        taskinfo.setTaskIdArgIndexes(1);//####[44]####
        taskinfo.addDependsOn(i);//####[44]####
        taskinfo.setParameters(pi, i);//####[44]####
        taskinfo.setMethod(__pt__parataskTest_ParIteratorString_int_method);//####[44]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[44]####
    }//####[44]####
    public static TaskID<Void> parataskTest(ParIterator<String> pi, BlockingQueue<Integer> i) {//####[44]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[44]####
        return parataskTest(pi, i, new TaskInfo());//####[44]####
    }//####[44]####
    public static TaskID<Void> parataskTest(ParIterator<String> pi, BlockingQueue<Integer> i, TaskInfo taskinfo) {//####[44]####
        // ensure Method variable is set//####[44]####
        if (__pt__parataskTest_ParIteratorString_int_method == null) {//####[44]####
            __pt__parataskTest_ParIteratorString_int_ensureMethodVarSet();//####[44]####
        }//####[44]####
        taskinfo.setQueueArgIndexes(1);//####[44]####
        taskinfo.setIsPipeline(true);//####[44]####
        taskinfo.setParameters(pi, i);//####[44]####
        taskinfo.setMethod(__pt__parataskTest_ParIteratorString_int_method);//####[44]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[44]####
    }//####[44]####
    public static TaskID<Void> parataskTest(TaskID<ParIterator<String>> pi, BlockingQueue<Integer> i) {//####[44]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[44]####
        return parataskTest(pi, i, new TaskInfo());//####[44]####
    }//####[44]####
    public static TaskID<Void> parataskTest(TaskID<ParIterator<String>> pi, BlockingQueue<Integer> i, TaskInfo taskinfo) {//####[44]####
        // ensure Method variable is set//####[44]####
        if (__pt__parataskTest_ParIteratorString_int_method == null) {//####[44]####
            __pt__parataskTest_ParIteratorString_int_ensureMethodVarSet();//####[44]####
        }//####[44]####
        taskinfo.setQueueArgIndexes(1);//####[44]####
        taskinfo.setIsPipeline(true);//####[44]####
        taskinfo.setTaskIdArgIndexes(0);//####[44]####
        taskinfo.addDependsOn(pi);//####[44]####
        taskinfo.setParameters(pi, i);//####[44]####
        taskinfo.setMethod(__pt__parataskTest_ParIteratorString_int_method);//####[44]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[44]####
    }//####[44]####
    public static TaskID<Void> parataskTest(BlockingQueue<ParIterator<String>> pi, BlockingQueue<Integer> i) {//####[44]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[44]####
        return parataskTest(pi, i, new TaskInfo());//####[44]####
    }//####[44]####
    public static TaskID<Void> parataskTest(BlockingQueue<ParIterator<String>> pi, BlockingQueue<Integer> i, TaskInfo taskinfo) {//####[44]####
        // ensure Method variable is set//####[44]####
        if (__pt__parataskTest_ParIteratorString_int_method == null) {//####[44]####
            __pt__parataskTest_ParIteratorString_int_ensureMethodVarSet();//####[44]####
        }//####[44]####
        taskinfo.setQueueArgIndexes(0, 1);//####[44]####
        taskinfo.setIsPipeline(true);//####[44]####
        taskinfo.setParameters(pi, i);//####[44]####
        taskinfo.setMethod(__pt__parataskTest_ParIteratorString_int_method);//####[44]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[44]####
    }//####[44]####
    public static void __pt__parataskTest(ParIterator<String> pi, int i) {//####[44]####
        pi.localBreak();//####[46]####
        System.out.println("start of task " + i);//####[47]####
        while (pi.hasNext()) //####[48]####
        {//####[48]####
            String elementString = pi.next();//####[49]####
            System.out.println("task " + i + " is es " + elementString);//####[50]####
        }//####[51]####
        System.out.println("end of task " + i);//####[52]####
    }//####[53]####
//####[53]####
//####[55]####
    private static volatile Method __pt__oneoff_hello__method = null;//####[55]####
    private synchronized static void __pt__oneoff_hello__ensureMethodVarSet() {//####[55]####
        if (__pt__oneoff_hello__method == null) {//####[55]####
            try {//####[55]####
                __pt__oneoff_hello__method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__oneoff_hello", new Class[] {//####[55]####
                    //####[55]####
                });//####[55]####
            } catch (Exception e) {//####[55]####
                e.printStackTrace();//####[55]####
            }//####[55]####
        }//####[55]####
    }//####[55]####
    private static TaskID<Void> oneoff_hello() {//####[55]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[55]####
        return oneoff_hello(new TaskInfo());//####[55]####
    }//####[55]####
    private static TaskID<Void> oneoff_hello(TaskInfo taskinfo) {//####[55]####
        // ensure Method variable is set//####[55]####
        if (__pt__oneoff_hello__method == null) {//####[55]####
            __pt__oneoff_hello__ensureMethodVarSet();//####[55]####
        }//####[55]####
        taskinfo.setParameters();//####[55]####
        taskinfo.setMethod(__pt__oneoff_hello__method);//####[55]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[55]####
    }//####[55]####
    public static void __pt__oneoff_hello() {//####[55]####
        hello("One-off Task");//####[56]####
    }//####[57]####
//####[57]####
//####[59]####
    private static volatile Method __pt__oneoff_hello2__method = null;//####[59]####
    private synchronized static void __pt__oneoff_hello2__ensureMethodVarSet() {//####[59]####
        if (__pt__oneoff_hello2__method == null) {//####[59]####
            try {//####[59]####
                __pt__oneoff_hello2__method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__oneoff_hello2", new Class[] {//####[59]####
                    //####[59]####
                });//####[59]####
            } catch (Exception e) {//####[59]####
                e.printStackTrace();//####[59]####
            }//####[59]####
        }//####[59]####
    }//####[59]####
    private TaskID<Void> oneoff_hello2() {//####[59]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[59]####
        return oneoff_hello2(new TaskInfo());//####[59]####
    }//####[59]####
    private TaskID<Void> oneoff_hello2(TaskInfo taskinfo) {//####[59]####
        // ensure Method variable is set//####[59]####
        if (__pt__oneoff_hello2__method == null) {//####[59]####
            __pt__oneoff_hello2__ensureMethodVarSet();//####[59]####
        }//####[59]####
        taskinfo.setParameters();//####[59]####
        taskinfo.setMethod(__pt__oneoff_hello2__method);//####[59]####
        taskinfo.setInstance(this);//####[59]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[59]####
    }//####[59]####
    public void __pt__oneoff_hello2() {//####[59]####
        System.out.println("Hello from oneoff_hello2");//####[60]####
    }//####[61]####
//####[61]####
//####[63]####
    private static volatile Method __pt__multi_hello__method = null;//####[63]####
    private synchronized static void __pt__multi_hello__ensureMethodVarSet() {//####[63]####
        if (__pt__multi_hello__method == null) {//####[63]####
            try {//####[63]####
                __pt__multi_hello__method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__multi_hello", new Class[] {//####[63]####
                    //####[63]####
                });//####[63]####
            } catch (Exception e) {//####[63]####
                e.printStackTrace();//####[63]####
            }//####[63]####
        }//####[63]####
    }//####[63]####
    private static TaskIDGroup<Void> multi_hello() {//####[63]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[63]####
        return multi_hello(new TaskInfo());//####[63]####
    }//####[63]####
    private static TaskIDGroup<Void> multi_hello(TaskInfo taskinfo) {//####[63]####
        // ensure Method variable is set//####[63]####
        if (__pt__multi_hello__method == null) {//####[63]####
            __pt__multi_hello__ensureMethodVarSet();//####[63]####
        }//####[63]####
        taskinfo.setParameters();//####[63]####
        taskinfo.setMethod(__pt__multi_hello__method);//####[63]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, 8);//####[63]####
    }//####[63]####
    public static void __pt__multi_hello() {//####[63]####
        hello("Multi-Task [subtask " + CurrentTask.relativeID() + "]  [thread " + CurrentTask.currentThreadID() + "]  [globalID " + CurrentTask.globalID() + "]  [mulTaskSize " + CurrentTask.multiTaskSize() + "]  [TaskID " + CurrentTask.currentTaskID() + "]  [ISinside? " + CurrentTask.insideTask() + "]  [progress " + CurrentTask.getProgress() + "]");//####[64]####
    }//####[65]####
//####[65]####
//####[67]####
    private static volatile Method __pt__interactive_hello__method = null;//####[67]####
    private synchronized static void __pt__interactive_hello__ensureMethodVarSet() {//####[67]####
        if (__pt__interactive_hello__method == null) {//####[67]####
            try {//####[67]####
                __pt__interactive_hello__method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt__interactive_hello", new Class[] {//####[67]####
                    //####[67]####
                });//####[67]####
            } catch (Exception e) {//####[67]####
                e.printStackTrace();//####[67]####
            }//####[67]####
        }//####[67]####
    }//####[67]####
    public static TaskID<Void> interactive_hello() {//####[67]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[67]####
        return interactive_hello(new TaskInfo());//####[67]####
    }//####[67]####
    public static TaskID<Void> interactive_hello(TaskInfo taskinfo) {//####[67]####
        // ensure Method variable is set//####[67]####
        if (__pt__interactive_hello__method == null) {//####[67]####
            __pt__interactive_hello__ensureMethodVarSet();//####[67]####
        }//####[67]####
        taskinfo.setParameters();//####[67]####
        taskinfo.setMethod(__pt__interactive_hello__method);//####[67]####
        taskinfo.setInteractive(true);//####[67]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[67]####
    }//####[67]####
    public static void __pt__interactive_hello() {//####[67]####
        hello("Interactive Task");//####[68]####
    }//####[69]####
//####[69]####
}//####[69]####
