import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IProperty } from '../property.model';
import { PropertyService } from '../service/property.service';

@Component({
  standalone: true,
  templateUrl: './property-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PropertyDeleteDialogComponent {
  property?: IProperty;

  constructor(
    protected propertyService: PropertyService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.propertyService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
