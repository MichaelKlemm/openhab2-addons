package org.openhab.io.homekit.internal.accessories;

import java.util.concurrent.CompletableFuture;

import org.eclipse.smarthome.core.items.ItemRegistry;
import org.eclipse.smarthome.core.library.items.ContactItem;
import org.eclipse.smarthome.core.library.types.OpenClosedType;
import org.openhab.io.homekit.internal.HomekitAccessoryUpdater;
import org.openhab.io.homekit.internal.HomekitTaggedItem;

import com.beowulfe.hap.HomekitCharacteristicChangeCallback;
import com.beowulfe.hap.accessories.ContactSensor;
import com.beowulfe.hap.accessories.properties.ContactState;

public class HomekitContactSensorImpl extends AbstractHomekitAccessoryImpl<ContactItem> implements ContactSensor {

    public HomekitContactSensorImpl(HomekitTaggedItem taggedItem, ItemRegistry itemRegistry,
            HomekitAccessoryUpdater updater) {
        super(taggedItem, itemRegistry, updater, ContactItem.class);
    }

    @Override
    public CompletableFuture<ContactState> getCurrentState() {
        OpenClosedType state = (OpenClosedType) getItem().getStateAs(OpenClosedType.class);
        ContactState targetState = (state == OpenClosedType.OPEN) ? ContactState.DETECTED : ContactState.NOT_DETECTED;
        return CompletableFuture.completedFuture(targetState);
    }

    @Override
    public void subscribeContactState(HomekitCharacteristicChangeCallback callback) {
        getUpdater().subscribe(getItem(), callback);
    }

    @Override
    public void unsubscribeContactState() {
        getUpdater().unsubscribe(getItem());
    }

}
