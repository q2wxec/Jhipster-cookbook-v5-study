import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGoalSettings } from 'app/shared/model/goal-settings.model';
import { GoalSettingsService } from './goal-settings.service';

@Component({
    selector: 'jhi-goal-settings-delete-dialog',
    templateUrl: './goal-settings-delete-dialog.component.html'
})
export class GoalSettingsDeleteDialogComponent {
    goalSettings: IGoalSettings;

    constructor(
        protected goalSettingsService: GoalSettingsService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.goalSettingsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'goalSettingsListModification',
                content: 'Deleted an goalSettings'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-goal-settings-delete-popup',
    template: ''
})
export class GoalSettingsDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ goalSettings }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(GoalSettingsDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.goalSettings = goalSettings;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/goal-settings', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/goal-settings', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
