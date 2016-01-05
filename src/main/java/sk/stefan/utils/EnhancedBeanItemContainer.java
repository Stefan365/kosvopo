package sk.stefan.utils;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.VaadinPropertyDescriptor;

import java.util.function.Consumer;

/**
 * Created by Lukas on 02.01.2016.
 */
public class EnhancedBeanItemContainer<BEANTYPE> extends BeanItemContainer {

    public EnhancedBeanItemContainer(Class type) throws IllegalArgumentException {
        super(type);
    }

    public void addGeneratedProperty(String propertyId, Class type, ValueGenerator<BEANTYPE> generator) {
        VaadinPropertyDescriptor<BEANTYPE> descriptor = new VaadinPropertyDescriptor<BEANTYPE>() {
            @Override
            public String getName() {
                return propertyId;
            }

            @Override
            public Class<?> getPropertyType() {
                return type;
            }

            @Override
            public Property<?> createProperty(BEANTYPE bean) {
                return new Property<Object>() {
                    @Override
                    public Object getValue() {
                        return generator.generate(bean);
                    }

                    @Override
                    public void setValue(Object newValue) throws ReadOnlyException {

                    }

                    @Override
                    public Class<?> getType() {
                        return null;
                    }

                    @Override
                    public boolean isReadOnly() {
                        return false;
                    }

                    @Override
                    public void setReadOnly(boolean newStatus) {

                    }
                };
            }
        };

        addContainerProperty(propertyId, descriptor);

    }

    public interface ValueGenerator<BEANTYPE> {
        Object generate(BEANTYPE item);
    }
}
