package com.xinchen.java.util;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2019/5/2 18:13
 */
public abstract class AbstractMap<K, V> implements Map<K, V> {

    protected AbstractMap() {
    }

    transient Set<K> keySet;
    transient Collection<V> values;

    /**
     * entrySet() 返回set<<Entry<K, V>>集合，放到子类中去实现
     *
     * @return
     */
    @Override
    public abstract Set<Entry<K, V>> entrySet();

    @Override
    public int size() {
        return entrySet().size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        // 获取迭代器
        Iterator<Entry<K, V>> iterator = entrySet().iterator();
        // 允许存在key = null 的情况
        if (null == key) {
            while (iterator.hasNext()) {
                Entry<K, V> next = iterator.next();
                if (next.getKey() == null) {
                    return true;
                }
            }
        } else {
            while (iterator.hasNext()) {
                Entry<K, V> next = iterator.next();
                if (key.equals(next.getKey())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        // 获取迭代器
        Iterator<Entry<K, V>> iterator = entrySet().iterator();
        // 允许value = null 的情况
        if (value == null) {
            while (iterator.hasNext()) {
                Entry<K, V> next = iterator.next();
                if (next.getValue() == null) {
                    return true;
                }
            }
        } else {
            while (iterator.hasNext()) {
                Entry<K, V> next = iterator.next();
                if (value.equals(next.getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        // 迭代遍历
        Iterator<Entry<K, V>> iterator = entrySet().iterator();
        if (key == null) {
            while (iterator.hasNext()) {
                final Entry<K, V> next = iterator.next();
                if (next.getKey() == null) {
                    return next.getValue();
                }
            }
        } else {
            while (iterator.hasNext()) {
                final Entry<K, V> next = iterator.next();
                if (key.equals(next.getKey())) {
                    return next.getValue();
                }

            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        // 抽象类中默认抛出异常
        // 具体实现放到子类中
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(Object key) {
        final Iterator<Entry<K, V>> i = entrySet().iterator();
        Map.Entry<K, V> correctEntry = null;
        if (key == null) {
            // 当当前Entry还未命中时，才继续遍历
            while (null == correctEntry && i.hasNext()) {
                final Entry<K, V> next = i.next();
                if (null == next.getKey()) {
                    correctEntry = next;
                }
            }
        } else {
            while (correctEntry == null && i.hasNext()) {
                final Entry<K, V> next = i.next();
                if (key.equals(next.getKey())) {
                    correctEntry = next;
                }
            }
        }

        // 移除命中值
        V oldValue = null;
        if (null != correctEntry) {
            oldValue = correctEntry.getValue();
            i.remove();
        }
        return oldValue;

    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> e : map.entrySet()) {
            // 遍历map，调用本类中的put加入所有
            put(e.getKey(), e.getValue());
        }
    }

    @Override
    public void clear() {
        entrySet().clear();
    }

    @Override
    public Set<K> keySet() {
        Set<K> ks = keySet;

        // 如果为空，则初始化
        if (null == ks) {
            ks = new AbstractSet<K>() {

                // 重新遍历
                @Override
                public Iterator<K> iterator() {
                    // 获取entrySet().iterator()
                    // 重写相关方法
                    return new Iterator<K>() {
                        private Iterator<Entry<K, V>> i = entrySet().iterator();

                        @Override
                        public boolean hasNext() {
                            return i.hasNext();
                        }

                        @Override
                        public K next() {
                            return i.next().getKey();
                        }

                        @Override
                        public void remove() {
                            i.remove();
                        }
                    };
                }

                @Override
                public int size() {
                    // 当前类的size()方法
                    return AbstractMap.this.size();
                }

                @Override
                public boolean isEmpty() {
                    return AbstractMap.this.isEmpty();
                }

                @Override
                public boolean contains(Object o) {
                    return AbstractMap.this.containsKey(o);
                }

                @Override
                public void clear() {
                    AbstractMap.this.clear();
                }
            };
            keySet = ks;
        }

        return ks;
    }

    @Override
    public Collection<V> values() {
        Collection<V> vals = values;

        // 如果为空则初始化
        if (vals == null) {
            vals = new AbstractCollection<V>() {
                // 重新迭代
                @Override
                public Iterator<V> iterator() {
                    return new Iterator<V>() {

                        // 本类中的迭代器
                        private Iterator<Entry<K, V>> i = entrySet().iterator();

                        @Override
                        public boolean hasNext() {
                            return i.hasNext();
                        }

                        @Override
                        public V next() {
                            return i.next().getValue();
                        }

                        @Override
                        public void remove() {
                            i.remove();
                        }
                    };
                }

                @Override
                public int size() {
                    return AbstractMap.this.size();
                }

                @Override
                public boolean isEmpty() {
                    return AbstractMap.this.isEmpty();
                }

                @Override
                public boolean contains(Object o) {
                    return AbstractMap.this.containsValue(o);
                }

                @Override
                public void clear() {
                    AbstractMap.this.clear();
                }

            };
            values = vals;
        }

        return vals;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        // 如果不属于Map类型返回false
        if (!(o instanceof Map)) {
            return false;
        }
        // 强制转换
        Map<?, ?> m = (Map<?, ?>) o;
        if (m.size() != size()) {
            return false;
        }

        try {
            // 迭代所有元素
            final Iterator<Entry<K, V>> i = entrySet().iterator();
            while (i.hasNext()) {
                final Entry<K, V> next = i.next();
                K key = next.getKey();
                V value = next.getValue();

                if (value == null) {
                    // 当value为空的时候
                    if (!(m.get(key) == null && m.containsKey(key))) {
                        return false;
                    }
                } else {
                    if (!value.equals(m.get(key))) {
                        return false;
                    }
                }

            }
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int h = 0;
        final Iterator<Entry<K, V>> iterator = entrySet().iterator();
        while (iterator.hasNext()) {
            h += iterator.next().hashCode();
        }
        return h;
    }

    @Override
    public String toString() {
        final Iterator<Entry<K, V>> iterator = entrySet().iterator();
        // 为空时返回
        if (!iterator.hasNext()) {
            return "{}";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (; ; ) {
            final Entry<K, V> next = iterator.next();
            final K key = next.getKey();
            final V value = next.getValue();
            sb.append(key == this ? "(this Map)" : key);
            sb.append('=');
            sb.append(values == this ? "(this Map)" : value);
            if (!iterator.hasNext()) {
                return sb.append('}').toString();
            }
            sb.append(',').append(' ');
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        AbstractMap<?, ?> result = (AbstractMap<?, ?>) super.clone();
        // keySet和values不被clone
        result.keySet = null;
        result.values = null;
        return result;
    }

    private static boolean eq(Object o1, Object o2) {
        return Objects.equals(o1, o2);
    }


}
