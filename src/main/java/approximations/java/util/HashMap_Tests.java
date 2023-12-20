package approximations.java.util;

import approximations.Test;

import java.io.Serializable;
import java.lang.Cloneable;
import java.lang.Object;
import java.lang.String;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

@Test
@SuppressWarnings({"unused"})
public final class HashMap_Tests {

    // internal variables

    // constructors

    /**
     * {@link java.util.HashMap#HashMap()}
     */
    @Test
    public static int test_HashMap_0(final int execution) {
        if (execution == 0) {
            HashMap<Object, Object> map = new HashMap<>();
            if (map.size() == 0)
                return 0;
            else
                return -1;
        }
        return -1;
    }


    /**
     * {@link java.util.HashMap#HashMap(Map)}
     */
    @Test
    public static int test_HashMap_1(final int execution) {
        if (execution == 0) {
            Hashtable<Object, Object> hashtable = new Hashtable<>();
            hashtable.put("London", 14_800_000);
            hashtable.put("Lisbon", 504_718);
            hashtable.put("Oslo", 634_293);
            HashMap<Object, Object> map = new HashMap<>(hashtable);
            if (map.size() == 3)
                return 0;
            else
                return -1;
        }
        return -1;
    }


    /**
     * {@link java.util.HashMap#HashMap(int)}
     */
    @Test
    public static int test_HashMap_2(final int execution) {
        if (execution == 0) {
            HashMap<Object, Object> map = new HashMap<>(21);
            if (map.size() == 0)
                return 0;
            else
                return -1;
        }

        if (execution == 1) {
            try {
                HashMap<Object, Object> map = new HashMap<>(-21);
            } catch (IllegalArgumentException e) {
                return 1;
            }
            return -1;
        }

        return -1;
    }


    /**
     * {@link java.util.HashMap#HashMap(int, float)}
     */
    @Test
    public static int test_HashMap_3(final int execution) {
        if (execution == 0) {
            HashMap<Object, Object> map = new HashMap<>(21, 0.75f);
            if (map.size() == 0)
                return 0;
            else
                return -1;
        }

        if (execution == 1) {
            try {
                HashMap<Object, Object> map = new HashMap<>(-21, 0.75f);
            } catch (IllegalArgumentException e) {
                return 1;
            }
            return -1;
        }

        if (execution == 2) {
            try {
                HashMap<Object, Object> map = new HashMap<>(21, -0.75f);
            } catch (IllegalArgumentException e) {
                return 2;
            }
            return -1;
        }

        if (execution == 3) {
            try {
                HashMap<Object, Object> map = new HashMap<>(21, Float.NaN);
            } catch (IllegalArgumentException e) {
                return 3;
            }
            return -1;
        }

        return -1;
    }


    // static methods

    // methods

    /**
     * {@link java.util.HashMap#clear()}
     */
    @Test
    public static int test_clear_0(final int execution) {
        if (execution == 0) {
            Hashtable<Object, Object> hashtable = new Hashtable<>();
            hashtable.put("London", 14_800_000);
            hashtable.put("Lisbon", 504_718);
            hashtable.put("Oslo", 634_293);
            HashMap<Object, Object> map = new HashMap<>(hashtable);
            map.clear();
            if (map.size() == 0)
                return 0;
            else
                return -1;
        }
        return -1;
    }


    /**
     * {@link java.util.HashMap#clone()}
     */
    @Test
    public static int test_clone_0(final int execution) {
        if (execution == 0) {
            Hashtable<String, Long> hashtable = new Hashtable<>();
            hashtable.put("London", 14_800_000L);
            hashtable.put("Lisbon", 504_718L);
            hashtable.put("Oslo", 634_293L);
            HashMap<String, Long> map = new HashMap<>(hashtable);
            HashMap<String, Long> clone = (HashMap<String, Long>) map.clone();
            if (clone.size() == 3 && clone.get("London") == 14_800_000L && clone.get("Lisbon") == 504_718L && clone.get("Oslo") == 634_293L)
                return 0;
            else
                return -1;
        }
        return -1;
    }


    /**
     * {@link java.util.HashMap#compute(Object, BiFunction)}
     */
    @Test
    public static int test_compute_0(final int execution) {
        Hashtable<String, Long> hashtable = new Hashtable<>();
        hashtable.put("London", 14_800_000L);
        hashtable.put("Lisbon", 504_718L);
        hashtable.put("Oslo", 634_293L);

        HashMap<String, Long> map = new HashMap<>(hashtable);

        if (execution == 0) {
            Long computed = map.compute("London", (x, y) -> x.length() + y);
            if (map.get("London") == 14_800_006L && computed == 14_800_006L)
                return 0;
        } else if (execution == 1) {
            Long computed = map.compute("NoneKey", (x, y) -> y != null ? x.length() + y : 666L);
            if (map.containsKey("NoneKey") && map.get("NoneKey") == 666L)
                return 1;
        } else if (execution == 2) {
            Long computed = map.compute("London", (x, y) -> null);
            if (!map.containsKey("London"))
                return 2;
        }
        return -1;
    }


    /**
     * {@link java.util.HashMap#computeIfAbsent(Object, Function)}
     */
    @Test
    public static int test_computeIfAbsent_0(final int execution) {
        Hashtable<String, Long> hashtable = new Hashtable<>();
        hashtable.put("London", 14_800_000L);
        hashtable.put("Lisbon", 504_718L);
        hashtable.put("Oslo", 634_293L);

        HashMap<String, Long> map = new HashMap<>(hashtable);

        if (execution == 0) {
            Long computed = map.computeIfAbsent("Tokyo", (x) -> x.length() + 1L);
            if (map.get("Tokyo") == 6L)
                return 0;
        } else if (execution == 1) {
            Long computed = map.computeIfAbsent("London", (x) -> 888L);
            if (map.get("London") == 14_800_000L)
                return 1;
        } else if (execution == 2) {
            map.put("AnyKeyWithNullValue", null);
            Long computed = map.computeIfAbsent("AnyKeyWithNullValue", (x) -> 888L);
            if (map.get("AnyKeyWithNullValue") == 888L)
                return 2;
        }

        return -1;
    }


    /**
     * {@link java.util.HashMap#computeIfPresent(Object, BiFunction)}
     */
    @Test
    public static int test_computeIfPresent_0(final int execution) {
        Hashtable<String, Long> hashtable = new Hashtable<>();
        hashtable.put("London", 14_800_000L);
        hashtable.put("Lisbon", 504_718L);
        hashtable.put("Oslo", 634_293L);

        HashMap<String, Long> map = new HashMap<>(hashtable);

        if (execution == 0) {
            Long computed = map.computeIfPresent("Oslo", (x, y) -> x.length() + 1L);
            if (computed == 5L && map.get("Oslo") == 5L)
                return 0;
        } else if (execution == 1) {
            Long computed = map.computeIfPresent("Absent", (x, y) -> 888L);
            if (computed == null)
                return 1;
        } else if (execution == 2) {
            map.put("AnyKeyWithNullValue", null);
            Long computed = map.computeIfPresent("AnyKeyWithNullValue", (x, y) -> 888L);
            if (computed == null && map.get("AnyKeyWithNullValue") == null)
                return 2;
        } else if (execution == 3) {
            Long computed = map.computeIfPresent("London", (x, y) -> null);
            if (computed == null && !map.containsKey("London"))
                return 3;
        }

        return -1;
    }


    /**
     * {@link java.util.HashMap#containsKey(Object)}
     */
    @Test
    public static int test_containsKey_0(final int execution) {
        Hashtable<String, Long> hashtable = new Hashtable<>();
        hashtable.put("London", 14_800_000L);
        hashtable.put("Lisbon", 504_718L);
        hashtable.put("Oslo", 634_293L);

        HashMap<String, Long> map = new HashMap<>(hashtable);

        if (execution == 0) {
            if (map.containsKey("London"))
                return 0;
        } else if (execution == 1) {
            if (!map.containsKey("Tokyo"))
                return 1;
        }
        return -1;
    }


    /**
     * {@link java.util.HashMap#containsValue(Object)}
     */
    @Test
    public static int test_containsValue_0(final int execution) {
        Hashtable<String, Long> hashtable = new Hashtable<>();
        hashtable.put("London", 14_800_000L);
        hashtable.put("Lisbon", 504_718L);
        hashtable.put("Oslo", 634_293L);

        HashMap<String, Long> map = new HashMap<>(hashtable);

        if (execution == 0) {
            if (map.containsValue(14_800_000L))
                return 0;
        } else if (execution == 1) {
            if (!map.containsValue(7L))
                return 1;
        }
        return -1;
    }


    /**
     * {@link java.util.HashMap#entrySet()}
     */
    @Test
    public static int test_entrySet_0(final int execution) {
        return -1;
    }


    /**
     * {@link java.util.HashMap#equals(Object)}
     */
    @Test
    public static int test_equals_0(final int execution) {
        HashMap<String, Integer> zoo_1 = new HashMap<>();
        zoo_1.put("Monkeys", 3);
        zoo_1.put("elephants", 1);

        HashMap<String, Integer> zoo_2 = new HashMap<>();
        zoo_2.put("Monkeys", 3);
        zoo_2.put("elephants", 1);

        if (execution == 0) {
            if (zoo_1.equals(zoo_2))
                return 0;
        } else if (execution == 1) {
            zoo_1.put("cats", 4);
            if (!zoo_1.equals(zoo_2))
                return 1;
        }

        return -1;
    }


    /**
     * {@link java.util.HashMap#forEach(BiConsumer)}
     */
    @Test
    public static int test_forEach_0(final int execution) {
        return -1;
    }


    /**
     * {@link java.util.HashMap#get(Object)}
     */
    @Test
    public static int test_get_0(final int execution) {
        HashMap<String, Integer> zoo_1 = new HashMap<>();
        zoo_1.put("Monkeys", 3);
        zoo_1.put("elephants", 1);

        if (execution == 0) {
            if (zoo_1.get("elephants") == 1)
                return 0;
        } else if (execution == 1) {
            if (zoo_1.get("cats") == null)
                return 1;
        }
        return -1;
    }


    /**
     * {@link java.util.HashMap#getOrDefault(Object, Object)}
     */
    @Test
    public static int test_getOrDefault_0(final int execution) {
        HashMap<String, Integer> zoo_1 = new HashMap<>();
        zoo_1.put("Monkeys", 3);
        zoo_1.put("elephants", 1);

        if (execution == 0) {
            if (zoo_1.getOrDefault("elephants", 666) == 1)
                return 0;
        } else if (execution == 1) {
            if (zoo_1.getOrDefault("cats", 666) == 666)
                return 1;
        }
        return -1;
    }


    /**
     * {@link java.util.HashMap#hashCode()}
     */
    @Test
    public static int test_hashCode_0(final int execution) {
        return -1;
    }


    /**
     * {@link java.util.HashMap#isEmpty()}
     */
    @Test
    public static int test_isEmpty_0(final int execution) {
        HashMap<String, Integer> zoo_1 = new HashMap<>();
        if (execution == 0) {
            if (zoo_1.isEmpty())
                return 0;
        } else if (execution == 1) {
            zoo_1.put("AnyAnimal", 1);
            if (!zoo_1.isEmpty())
                return 1;
        }
        return -1;
    }


    /**
     * {@link java.util.HashMap#keySet()}
     */
    @Test
    public static int test_keySet_0(final int execution) {
        return -1;
    }


    /**
     * {@link java.util.HashMap#merge(Object, Object, BiFunction)}
     */
    @Test
    public static int test_merge_0(final int execution) {
        return -1;
    }


    /**
     * {@link java.util.HashMap#put(Object, Object)}
     */
    @Test
    public static int test_put_0(final int execution) {
        return -1;
    }


    /**
     * {@link java.util.HashMap#putAll(Map)}
     */
    @Test
    public static int test_putAll_0(final int execution) {
        return -1;
    }


    /**
     * {@link java.util.HashMap#putIfAbsent(Object, Object)}
     */
    @Test
    public static int test_putIfAbsent_0(final int execution) {
        return -1;
    }


    /**
     * {@link java.util.HashMap#remove(Object)}
     */
    @Test
    public static int test_remove_0(final int execution) {
        return -1;
    }


    /**
     * {@link java.util.HashMap#remove(Object, Object)}
     */
    @Test
    public static int test_remove_1(final int execution) {
        return -1;
    }


    /**
     * {@link java.util.HashMap#replace(Object, Object)}
     */
    @Test
    public static int test_replace_0(final int execution) {
        return -1;
    }


    /**
     * {@link java.util.HashMap#replace(Object, Object, Object)}
     */
    @Test
    public static int test_replace_1(final int execution) {
        return -1;
    }


    /**
     * {@link java.util.HashMap#replaceAll(BiFunction)}
     */
    @Test
    public static int test_replaceAll_0(final int execution) {
        return -1;
    }


    /**
     * {@link java.util.HashMap#size()}
     */
    @Test
    public static int test_size_0(final int execution) {
        return -1;
    }


    /**
     * {@link java.util.HashMap#toString()}
     */
    @Test
    public static int test_toString_0(final int execution) {
        return -1;
    }


    /**
     * {@link java.util.HashMap#values()}
     */
    @Test
    public static int test_values_0(final int execution) {
        return -1;
    }

}