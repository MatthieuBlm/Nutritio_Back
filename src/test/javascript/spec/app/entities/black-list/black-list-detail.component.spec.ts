/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { NutritioBackTestModule } from '../../../test.module';
import { BlackListDetailComponent } from '../../../../../../main/webapp/app/entities/black-list/black-list-detail.component';
import { BlackListService } from '../../../../../../main/webapp/app/entities/black-list/black-list.service';
import { BlackList } from '../../../../../../main/webapp/app/entities/black-list/black-list.model';

describe('Component Tests', () => {

    describe('BlackList Management Detail Component', () => {
        let comp: BlackListDetailComponent;
        let fixture: ComponentFixture<BlackListDetailComponent>;
        let service: BlackListService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [NutritioBackTestModule],
                declarations: [BlackListDetailComponent],
                providers: [
                    BlackListService
                ]
            })
            .overrideTemplate(BlackListDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BlackListDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BlackListService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new BlackList(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.blackList).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
