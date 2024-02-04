import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IReview } from '../review.model';
import { ReviewService } from '../service/review.service';

@Component({
  standalone: true,
  templateUrl: './review-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ReviewDeleteDialogComponent {
  review?: IReview;

  constructor(
    protected reviewService: ReviewService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.reviewService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
