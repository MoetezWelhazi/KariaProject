import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IKariaUser } from '../karia-user.model';
import { KariaUserService } from '../service/karia-user.service';

@Component({
  standalone: true,
  templateUrl: './karia-user-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class KariaUserDeleteDialogComponent {
  kariaUser?: IKariaUser;

  constructor(
    protected kariaUserService: KariaUserService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.kariaUserService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
