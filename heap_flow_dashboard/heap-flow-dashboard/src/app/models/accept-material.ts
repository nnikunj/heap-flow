import { Vendor } from './vendor';
import { InventoryItem } from './inventory-item';

export interface AcceptMaterial {
    vendor: Vendor;
    invoice: string;
    grn: string;
    recordDate: string;

    quantity: Number;
    price: Number;

    acceptingMaterialCode: string;
    classification: string;

    items: InventoryItem[];

}
