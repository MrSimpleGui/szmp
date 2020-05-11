package com.webdrp.controller.sys;

import com.webdrp.annotation.SysUserAnnotation;
import com.webdrp.common.BaseController;
import com.webdrp.common.BaseService;
import com.webdrp.common.Pager;
import com.webdrp.common.Result;
import com.webdrp.entity.Grade;
import com.webdrp.entity.Member;
import com.webdrp.entity.User;
import com.webdrp.entity.vo.MemberSuperiorVo;
import com.webdrp.entity.vo.MemberSysVo;
import com.webdrp.err.BusinessException;
import com.webdrp.service.GradeService;
import com.webdrp.service.MerchantAnalysisService;
import com.webdrp.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by yuanming on 2018/8/3.
 */
@Api(tags = "后台接口微信用户相关")
@RestController
@RequestMapping("/sys/richuser")
public class MemberController extends BaseController<Member,Integer> {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MemberService memberService;

    @Autowired
    GradeService gradeService;

    /**
     * 抽象业务类  T 实体对象   ID为
     * @return
     */
    @Override
    public BaseService<Member> getService() {
        return memberService;
    }

    @ApiOperation("更新微信用户状态, status为1的时候为禁用，0为放开")
    @PostMapping("/updateStatus")
    public Result updateStatus(@RequestHeader(value="token") String token, @RequestParam Integer richUserId,@RequestParam Integer status){
        Member member = new Member();
        member.setId(richUserId);
        member.setStatus(status);
        memberService.setRichUserStatus(member);
        return Result.success();
    }

    @Autowired
    MerchantAnalysisService merchantAnalysisService;

    @ApiOperation("更新微信用户等级")
    @PostMapping("/updateGrade")
    public Result updateGrade(@RequestHeader(value="token") String token, @SysUserAnnotation User user, @RequestParam Integer richUserId,@RequestParam Integer grade){
        logger.error("管理员调控人员等级，操作人员："+user.getUsername());
        Member updateUser = memberService.findOne(richUserId);
        Member member = new Member();
        member.setId(richUserId);
        member.setGrade(grade);
        memberService.update(member);
        return Result.success();
    }

    @ApiOperation("更新微信用户直推")
    @PostMapping("/updateZid")
    public Result updateZid(@RequestHeader(value="token") String token, @SysUserAnnotation User user, @RequestParam Integer richUserId,@RequestParam Integer zid){
        logger.error("管理员调控人员直推，操作人员："+user.getUsername());
        Member member = new Member();
        member.setId(richUserId);
        Member temp = memberService.findOne(zid);
        if (Objects.isNull(temp)){
            throw  new BusinessException("zid直推的用户不存在！");
        }
        member.setZid(zid);
        String path = temp.getPath();
        if (StringUtils.isEmpty(path)){
            path = "" + temp.getId();
        }else{
            path = path + "," + temp.getId();
        }
        member.setPath(path);
        memberService.update(member);
        return Result.success();
    }

    @ApiOperation("设置微信用户表的ID下一个初始值增加多少")
    @PostMapping("/setAutoInCreate")
    public Result setAutoInCreate(@RequestHeader(value="token") String token, @RequestParam Integer addnum){
        memberService.setAutoCreate(addnum);
        return Result.success(addnum);
    }
    @ApiOperation("增加用户佣金，可以正负数负数就减少")
    @PostMapping("/addMoneyToRichUser")
    public Result addUserWallet(@RequestHeader(value="token") String token, @SysUserAnnotation User user, @RequestParam Integer richUserId, @RequestParam Integer money){
        logger.error("【addUserWallet】richUserId="+richUserId+"   money="+money);
        logger.error("管理员调控佣金，操作人员："+user.getUsername());
        memberService.addUserWallet(richUserId,money);
        return Result.success();
    }

    @ApiOperation("获取微信的最大用户ID")
    @GetMapping("/getMaxId")
    public Result getMaxId(@RequestHeader(value="token") String token){
        return Result.success(memberService.findMaxId());
    }

    @ApiOperation("获取微信用户的等级映射关系 K-V")
    @GetMapping("/getGradeList")
    public Result getGradeList(@RequestHeader(value="token") String token){
        Map<Integer,String> gradeList = new HashMap<>();
        List<Grade> gradeList1 = gradeService.findAll(new Grade());
        for (int i = 0 ; i < gradeList1.size();i++){
            gradeList.put(gradeList1.get(i).getRank(),gradeList1.get(i).getName());
        }
        return Result.success(gradeList);
    }

    @ApiOperation("大区经理 城市经理查询接口")
    @GetMapping("/findByPageManager")
    public Result findByPageManager(@RequestHeader(value="token") String token, Member member, Pager pager){
        List<MemberSysVo> list = memberService.findSysRichUser(member,pager);
        return Result.success().addAttribute("list",list).addAttribute("pager",pager);
    }


    @ApiOperation("查询用户的团队")
    @GetMapping("/findTeamUserByPage")
    public Result findTeamUserByPage(@RequestHeader(value="token") String token, @RequestParam Integer id, Pager pager){
        Member item = new Member();
        int first = memberService.findTeamUserOrderCountFirst(item);
        int team = memberService.findTeamUserOrderCountV1(item);

//        List<Member> list = memberService.findTeamUserByPage(id,pager);
        List<MemberSuperiorVo> list = memberService.findTeamMemberVoByPage(id, pager);
        return Result.success()
                .addAttribute("list",list)
                .addAttribute("pager",pager)
                .addAttribute("first",first)
                .addAttribute("team",team);
    }

    @ApiOperation("查询用户的团队")
    @GetMapping("/findSuperiorByUserId")
    public Result findeSuperiorByUserId(@RequestHeader(value="token") String token, Integer id){
        List<MemberSuperiorVo> list = memberService.findSuperiorById(id);
        return Result.success().addAttribute("list",list);
    }

    @ApiOperation("分页带条件查询所有，带推荐人信息")
//    @GetMapping("/findSuperiorByPage")
    public Result loadAll(@RequestHeader(value="token") String token, Member member, Pager pager){
        MemberSuperiorVo memberSuperiorVo = new MemberSuperiorVo();
        BeanUtils.copyProperties(member, memberSuperiorVo);
        List<MemberSuperiorVo> list = memberService.loadAll(memberSuperiorVo,pager);
        return Result.success().addAttribute("pager",pager).addAttribute("list",list);
    }

    @ApiOperation("清除所有用户推广二维码")
    @PostMapping("/clearQrCode")
    public Result clearQrCode(@RequestHeader(value="token") String token){
        memberService.clearAllQrCode();
        return Result.success();
    }

    @ApiOperation("清除某一个用户推广二维码 id=> 用户ID")
    @PostMapping("/clearOneQrCode")
    public Result clearOneQrCode(@RequestHeader(value="token") String token,@RequestParam Integer id){
        memberService.clearOneQrCode(id);
        return Result.success();
    }



}
