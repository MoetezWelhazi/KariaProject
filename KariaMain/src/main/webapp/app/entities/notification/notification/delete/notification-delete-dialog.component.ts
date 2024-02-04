import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { INotification } from '../notification.model';
import { NotificationService } from '../service/notification.service';

@Component({
  standalone: true,
  templateUrl: './notification-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class NotificationDeleteDialogComponent {
  notification?: INotification;

  constructor(
    protected notificationService: NotificationService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.notificationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
