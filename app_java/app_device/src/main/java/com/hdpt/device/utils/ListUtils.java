package com.hdpt.device.utils;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Joiner;
import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class ListUtils {
    /**
     * list 删除null
     *
     * @param list
     * @return
     */
    public static List<String> removeNull(List<String> list) {
        if (null == list) {
            return null;
        }
        Iterables.removeIf(list, Objects::isNull);
        return list;
    }

    /**
     * 去重和去除 null
     *
     * @param list
     * @return
     */
    public static List<String> distinctNull(List<String> list) {
        if (null == list) {
            return null;
        }
        return ImmutableSet.copyOf(Iterables.filter(list, Predicates.not(Predicates.isNull()))).asList();
    }

    /**
     * 去重和去除 null
     *
     * @param list
     * @return
     */
    public static Optional<List<String>> distinctNullOptional(List<String> list) {
        if (null == list) {
            return Optional.empty();
        }
        return Optional.ofNullable(ImmutableSet.copyOf(Iterables.filter(list, Predicates.not(Predicates.isNull()))).asList());
    }

    /**
     * 拆分 逗号 数组，并去重
     *
     * @param str
     * @return
     */
    public static Optional<List<String>> parse(String str) {
        return parse(str, ",");
    }

    /**
     * 拆分 逗号 数组，并去重
     *
     * @param str
     * @return
     */
    public static Optional<List<String>> parse(String str, String separator) {
        List<String> strings = Splitter.on(separator).trimResults().omitEmptyStrings().splitToList(str);
        if (null == strings || strings.size() <= 0) {
            return Optional.empty();
        }
        return Optional.ofNullable(strings.stream().distinct().collect(Collectors.toList()));
    }

    /**
     * list 获取 ids 数组
     *
     * @param list
     * @param keyMapper
     * @param <K>
     * @param <T>
     * @return
     */
    public static <K, T> Optional<List<K>> parse(List<T> list, Function<? super T, ? extends K> keyMapper) {
        return Optional.ofNullable(list.stream().map(keyMapper).collect(Collectors.toList()));
    }

    /**
     * list 获取 ids 数组 并去重
     *
     * @param list
     * @param keyMapper
     * @param <K>
     * @param <T>
     * @return
     */
    public static <K, T> Optional<List<K>> parseByDistinct(List<T> list, Function<? super T, ? extends K> keyMapper) {
        return Optional.ofNullable(list.stream().map(keyMapper).distinct().collect(Collectors.toList()));
    }

    /**
     * list 转换为 map
     *
     * @param list
     * @param keyMapper
     * @param <K>
     * @param <T>
     * @return
     */
    public static <K, T> Map<K, T> toMap(List<T> list, Function<? super T, ? extends K> keyMapper) {
        return list.stream().collect(Collectors.toMap(keyMapper, Function.identity(),
                (existing, replacement) -> existing));
    }

    /**
     * list 转换为 map
     *
     * @param list
     * @param keyMapper
     * @param keyMapper2
     * @param <K>
     * @param <K2>
     * @param <T>
     * @return
     */
    public static <K, K2, T> Map<K, K2> toMap(List<T> list, Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends K2> keyMapper2) {
        return list.stream().collect(Collectors.toMap(keyMapper, keyMapper2, (key1, key2) -> key2));
    }

    /**
     * 解析后，用 , 号分割,拼接(连接)成 字符串，并跳过 null 值
     *
     * @param list
     * @return
     */
    public static String toString(List<String> list) {
        return toString(list, ",");
    }

    /**
     * 解析后，用 指定 符号分割,拼接(连接)成 字符串，并跳过 null 值
     *
     * @param list
     * @return
     */
    public static String toString(List<String> list, String separator) {
        return Joiner.on(separator).skipNulls().join(list);
    }
    /**
     * @param targetList * 要排序的实体类List集合
     * @param sortField * 排序字段(实体类属性名)
     * @param sortMode * true正序，false逆序
     */
    @SuppressWarnings("all")
    public static <T> void sort(List<T> targetList, final String sortField, final boolean sortMode) {
        if (targetList == null || targetList.size() < 2 || sortField == null || sortField.length() == 0) {
            return;
        }
        Collections.sort(targetList, new Comparator() {
            @Override
            public int compare(Object obj1, Object obj2) {
                int retVal = 0;
                try { // 获取getXxx()方法名称
                    String methodStr = "get" + sortField.substring(0, 1).toUpperCase() + sortField.substring(1);
                    //方法不存在会报NoSuchMethodException
                    Method method1 = ((T) obj1).getClass().getMethod(methodStr, null);
                    //System.out.println("method1 : "+method1);
                    Method method2 = ((T) obj2).getClass().getMethod(methodStr, null);
                    // System.out.println("method2 : "+method2);
                    if (sortMode) {
                        retVal = method1.invoke(((T) obj1), null).toString().compareTo(method2.invoke(((T) obj2), null).toString());
                    } else {
                        retVal = method2.invoke(((T) obj2), null).toString().compareTo(method1.invoke(((T) obj1), null).toString());
                    }
                } catch (Exception e) {
                    System.out.println("List<" + ((T) obj1).getClass().getName() + ">排序异常！");
                    e.printStackTrace();
                }
                return retVal;
            }
        });
    }

    /**
     * @describe 依据某个字段对集合进行排序
     * @date 2013-1-22 下午3:44:47
     * @param list
     *            待排序的集合
     * @param fieldName
     *            依据这个字段进行排序
     * @param asc
     *            如果为true，是正序；为false，为倒序
     */
    @SuppressWarnings("unchecked")
    public static <T> void sortList(List<T> list, String fieldName, boolean asc) {
        Comparator<?> mycmp = ComparableComparator.getInstance();
        // 允许null
        mycmp = ComparatorUtils.nullLowComparator(mycmp);
        if (!asc) {
            // 逆序
            mycmp = ComparatorUtils.reversedComparator(mycmp);
        }
        Collections.sort(list, new BeanComparator(fieldName, mycmp));
    }
    /**
     * 创建集合实例
     * @return 集合实例
     */
    public static <E> LinkedList<E> newLinkedList() {
        return Lists.newLinkedList();
    }

    /**
     * 创建集合实例
     * @param elements 集合
     * @return 集合实例
     */
    public static <E> LinkedList<E> newLinkedList(Iterable<? extends E> elements) {
        return Lists.newLinkedList(elements);
    }

    /**
     * 创建集合实例
     * @return 集合实例
     */
    public static <E> ArrayList<E> newArrayList() {
        return Lists.newArrayList();
    }

    /**
     * 创建集合实例
     * @param elements 接受可变参数
     * @return 集合实例
     */
    @SafeVarargs
    public static <E> ArrayList<E> newArrayList(E... elements) {
        return Lists.newArrayList(elements);
    }

    /**
     * 创建集合实例
     * @param elements 集合
     * @return 集合实例
     */
    public static <E> ArrayList<E> newArrayList(Iterable<? extends E> elements) {
        return Lists.newArrayList(elements);
    }

    /**
     * 创建集合实例（指定初始化长度和默认值）
     * @param defaultValue 默认值
     * @param length 长度
     * @param <E> 泛型
     * @return 集合实例
     */
    public static <E> ArrayList<E> initDefaultArrayList(E defaultValue, Integer length) {
        ArrayList<E> list = new ArrayList<>(length);
        if(defaultValue!=null) {
            for (int i = 0; i < length; i++) {
                list.add(defaultValue);
            }
        }
        return list;
    }

    /**
     * 将逗号分隔的ids字符串转换成Integer类型的List集合
     * @param stringIdsString ids字符串
     * @return
     */
    public static List<Integer> splitStringIdsStringAsIntegerList(String stringIdsString) {
        List<Integer> longIds = new ArrayList();
        if (StringUtils.isBlank(stringIdsString)) {
            return longIds;
        }
        String[] stringIds = stringIdsString.split(",");
        for (String stringId : stringIds) {
            if (StringUtils.isNotBlank(stringId)) {
                longIds.add(Integer.parseInt(stringId));
            }
        }
        return longIds;
    }

    /**
     * 将逗号分隔的ids字符串转换成Long类型的List集合
     * @param stringIdsString ids字符串
     * @return
     */
    public static List<Long> splitStringIdsStringAsLongList(String stringIdsString) {
        List<Long> longIds = new ArrayList();
        if (StringUtils.isBlank(stringIdsString)) {
            return longIds;
        }
        String[] stringIds = stringIdsString.split(",");
        for (String stringId : stringIds) {
            if (StringUtils.isNotBlank(stringId)) {
                longIds.add(Long.parseLong(stringId));
            }
        }
        return longIds;
    }

    /**
     * 将逗号分隔的字符串转换成String类型的List集合
     * @param strings 逗号分隔字符串
     * @return
     */
    public static List<String> splitStringsAsStringList(String strings) {
        return splitStringsAsStringList(strings, ",");
    }

    /**
     * 将逗号分隔的字符串转换成String类型的List集合
     * @param strings 逗号分隔字符串
     * @return
     */
    public static List<String> splitStringsAsStringList(String strings, String separator) {
        if(StringUtils.isBlank(separator)){
            return null;
        }
        List<String> stringList = ListUtils.newArrayList();
        if (StringUtils.isBlank(strings)) {
            return stringList;
        }
        String[] stringArray = strings.split(separator);
        for (String str : stringArray) {
            if (StringUtils.isNotBlank(str)) {
                stringList.add(str);
            }
        }
        return stringList;
    }

    /**
     * 将逗号分隔的字符串转换成String类型的List集合
     * @param strings 逗号分隔字符串
     * @return
     */
    public static String[] splitStringsAsStringArray(String strings, String separator) {
        if(StringUtils.isBlank(separator)){
            return null;
        }
        if (StringUtils.isBlank(strings)) {
            return new String[]{};
        }
        return strings.split(separator);
    }


    /**
     * 将集合浅克隆 (引用的复制)
     * @param list 被复制的集合
     * @param <T> 实体类型
     * @return List
     */
    public static <T> List<T> clone(List<T> list) {
        List<T> result = ListUtils.newArrayList();
        result.addAll(list);
        return result;
    }

    /**
     * 求集合并集
     * @param list1 集合1
     * @param list2 集合2
     * @param <T> 泛型类型
     * @return ArrayList
     */
    public static <T> List<T> addAll(List<T> list1, List<T> list2) {
        List<T> resultList = ListUtils.newArrayList();
        if (CollectionUtils.isNotEmpty(list1)) {
            resultList.addAll(list1);
        }
        if (CollectionUtils.isNotEmpty(list2)) {
            resultList.addAll(list2);
        }
        return resultList;
    }

    /**
     * 求集合交集
     * @param list1 集合1
     * @param list2 集合2
     * @param <T> 泛型类型
     * @return ArrayList
     */
    public static <T> List<T> retainAll(List<T> list1, List<T> list2) {
        if (CollectionUtils.isEmpty(list1) || CollectionUtils.isEmpty(list2)) {
            return ListUtils.newArrayList();
        }
        List<T> resultList = ListUtils.newArrayList(list1);
        resultList.retainAll(list2);
        return resultList;
    }

    /**
     * 求集合差集 （集合1减去集合2）
     * @param list1 集合1
     * @param list2 集合2
     * @param <T> 泛型类型
     * @return ArrayList
     */
    public static <T> List<T> removeAll(List<T> list1, List<T> list2) {
        if (CollectionUtils.isEmpty(list1) || CollectionUtils.isEmpty(list2)) {
            return list1;
        }
        List<T> resultList = ListUtils.newArrayList(list1);
        resultList.removeAll(list2);
        return resultList;
    }

    /**
     * 由一个集合(类型F)转List(类型T)
     * @param fromCollection 转换前的集合
     * @param function 转换方法
     * @param <F> 转换前的实体类型
     * @param <T> 转换后的实体类型
     * @return Set
     */
    public static <F, T> List<T> transform(Collection<F> fromCollection, Function<? super F, ? extends T> function) {
        if (CollectionUtils.isEmpty(fromCollection)) {
            return ListUtils.newArrayList();
        }
        return fromCollection.stream().map(function).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 由一个集合(类型T)过滤后转List(类型T)
     * @param collection 集合
     * @param predicate 过滤条件
     * @param <T> 类型
     * @return Set
     */
    public static <T> List<T> filter(Collection<T> collection, Predicate<? super T> predicate) {
        if (CollectionUtils.isEmpty(collection)) {
            return ListUtils.newArrayList();
        }
        return collection.stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * 由一个集合(类型T)去重后转List(类型T)
     * @param collection 集合(类型T)
     * @param fun 转换方法
     * @param <F> 转换前的类型
     * @param <T> 转换后的类型
     * @return Set
     */
    public static <F, T> List<T> transformDistinct(Collection<F> collection, Function<? super F, ? extends T> fun) {
        if (CollectionUtils.isEmpty(collection)) {
            return ListUtils.newArrayList();
        }
        return collection.stream().map(fun).filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    /**
     * 获取列表中第一个满足条件的元素
     * @param list 列表
     * @param predicate 条件
     * @param defaultValue 找不到返回默认值
     * @param <T> 元素类型
     * @return 元素
     */
    public static <T> T findFirst(List<T> list, Predicate<? super T> predicate, T defaultValue) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.stream().filter(predicate).findFirst().orElse(defaultValue);
    }

    /**
     * 判断列表是否存在元素满足条件
     * @param list 列表
     * @param predicate 条件
     * @param <T> 元素类型
     * @return 元素
     */
    public static <T> Boolean anyMatch(List<T> list, Predicate<? super T> predicate) {
        if (CollectionUtils.isEmpty(list)) {
            return Boolean.FALSE;
        }
        return list.stream().anyMatch(predicate);
    }

    /**
     * 判断列表是否全部满足条件
     * @param list 列表
     * @param predicate 条件
     * @param <T> 元素类型
     * @return 元素
     */
    public static <T> Boolean allMatch(List<T> list, Predicate<? super T> predicate) {
        if (CollectionUtils.isEmpty(list)) {
            return Boolean.FALSE;
        }
        return list.stream().allMatch(predicate);
    }



    /**
     * 列表根据唯一约束去重 (如果有重复只取第一条记录)
     * @param list 列表
     * @param uniqueKey 唯一约束
     * @param <T> 元素类型
     * @return 去重后的列表
     */
    public static <T, K> List<T> listDeduplication(List<T> list, Function<? super T, K> uniqueKey) {
        Map<K, T> map = new HashMap<>();
        List<T> result = new ArrayList<>();
        for (T t : list) {
            if (t == null) {
                continue;
            }
            K key = uniqueKey.apply(t);
            if (key == null || map.containsKey(key)) {
                continue;
            }
            result.add(t);
            map.put(key, t);
        }
        return result;
    }

    /**
     * list排序
     *
     * @param list
     * @param <T>
     */
    public static <T extends Comparable<T>> void sort(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        Collections.sort(list);
    }

    /**
     * list排序
     *
     * @param list 需要排序的list
     * @param comparable 比较器
     * @param <T> 泛型
     */
    public static <T> void sort(List<T> list, Comparator<? super T> comparable) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.sort(comparable);
    }

    /**
     * list排序
     *
     * @param list 需要排序的list
     * @param getValue 获取value
     * @param <T> 元素的泛型
     * @param <F> value的泛型
     */
    public static <T, F extends Comparable<F>> void sort(List<T> list, Function<T, F> getValue) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.sort(Comparator.comparing(getValue));
    }

    /**
     * list排序
     *
     * @param list 需要排序的list
     * @param getValue 获取value
     * @param comparable 比较器
     * @param <T> 元素的泛型
     * @param <F> value的泛型
     */
    public static <T, F> void sort(List<T> list, Function<T, F> getValue, Comparator<? super F> comparable) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.sort(Comparator.comparing(getValue, comparable));
    }


    /**
     * list逆序翻转
     *
     * @param list
     * @param <T>
     */
    public static <T extends Comparable<T>> void reverse(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        Collections.reverse(list);
    }



}
